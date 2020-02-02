package doman.algorithm;

import actions.*;
import doman.TableItem;
import doman.rule.Rule;
import doman.rule.StartRule;
import lombok.Getter;
import lombok.ToString;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;
import utils.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static doman.TableItem.EPSILON;


@ToString
public class Table {
    private final String START;
    private static final TableItem END = new TableItem("end");

    private Map<Pair<ItemSet, String>, ItemSet> table = new HashMap<>();

    @Getter
    private Map<Pair<Integer, String>, Action> resTable;
    @Getter
    private List<Rule> rules;

    public Table(List<StartRule> startRules, Set<TableItem> tokens, String start) {
        START = start;
        List<Rule> tempRuleList = startRules.stream().map(StartRule::getRule).collect(Collectors.toList());
        List<Rule> ruleList = IntStreamEx.range(tempRuleList.size()).mapToObj(i -> new Rule(tempRuleList.get(i), i)).toList();
        this.rules = ruleList;

        List<TableItem> terminal = StreamEx.of(tokens)
                .filter(i -> Character.isUpperCase(i.getName().charAt(0)))
                .distinct()
                .append(EPSILON)
                .toList();

        Map<String, List<Rule>> rules = StreamEx.of(ruleList).groupingBy(Rule::getName);
        Rule startRule = new Rule(start, StreamEx.of(rules.get(start)).map(Rule::getTokens).map(rule -> rule.get(0)).toList());

        Set<ItemSet> itemSets = createItemSet(rules, tokens, startRule);

        Set<Rule> ruleWithTranslation = createTranslationTable(itemSets, startRule);

        Map<TableItem, Set<TableItem>> firstSets = createFirst(StreamEx.of(terminal).map(TableItem::getName).toSet(), ruleWithTranslation);
        Map<TableItem, Set<TableItem>> followSets = createFollow(firstSets, ruleWithTranslation, StreamEx.of(terminal).map(TableItem::getName).toSet());

        createActionsAndGoToTable(StreamEx.of(terminal).map(TableItem::getName).toSet(), StreamEx.of(ruleList).toSet(), ruleWithTranslation, followSets);
    }

    public void createActionsAndGoToTable(Set<String> terminals, Set<Rule> rules, Set<Rule> ruleWithTranslation, Map<TableItem, Set<TableItem>> followSets) {
        Map<Pair<Integer, String>, Action> resTable = new HashMap<>();

        table.forEach((k, v) -> {
            if (terminals.contains(k.getSecond())) {
                resTable.put(new Pair<>(k.getFirst().getId(), k.getSecond()), new Shift(v.getId()));
            } else {
                resTable.put(new Pair<>(k.getFirst().getId(), k.getSecond()), new GoTo(v.getId()));
            }
        });

        Map<Rule, Set<TableItem>> mergedRules = mergeRules(ruleWithTranslation, followSets);


        mergedRules.forEach((r, follow) -> {
            for (TableItem node : follow) {
                if (r.getName().equals(START)) {
                    resTable.put(new Pair<>(r.getId(), node.getName()), new Accept());
                } else {
                    resTable.put(new Pair<>(r.getId(), node.getName()), new Reduce(r.getCode(),
                            StreamEx.of(rules)
                                    .map(Rule::toProductionRule)
                                    .filter(rule -> rule.equalsRule(r))
                                    .findFirst()
                                    .get()));
                }
            }
        });

        this.resTable = resTable;
    }

    public static Map<Rule, Set<TableItem>> mergeRules(Set<Rule> rules, Map<TableItem, Set<TableItem>> followSets) {
        List<List<Rule>> res = new ArrayList<>();
        for (Rule rule : rules) {
            boolean added = false;
            for (List<Rule> group : res) {
                Rule tmp = group.get(0);
                if (tmp.getName().equals(rule.getName()) && tmp.getTokens().size() == rule.getTokens().size()) {
                    if ((tmp.getTableItems().get(tmp.getTableItems().size() - 1)).getToId() ==
                            (rule.getTableItems().get(rule.getTableItems().size() - 1)).getToId()) {

                        added = group.add(rule);
                        break;
                    }
                }
            }

            if (!added) {
                res.add(new ArrayList<>(List.of(rule)));
            }
        }

        Map<Rule, Set<TableItem>> mergedRulesAndFollow = new HashMap<>();
        res.forEach(l -> {
            l.get(0).setId((l.get(0).getTableItems().get(l.get(0).getTableItems().size() - 1)).getToId());
            Set<TableItem> mergedFollow = new HashSet<>();
            l.forEach(r -> mergedFollow.addAll(followSets.get(r.getTableItem())));
            mergedRulesAndFollow.put(l.get(0), mergedFollow);
        });
        return mergedRulesAndFollow;
    }

    public Map<TableItem, Set<TableItem>> createFirst(Set<String> terminals, Set<Rule> rules) {
        Map<TableItem, Set<TableItem>> result = new HashMap<>();
        for (Rule rule : rules) {
            result.put(rule.getTableItem(), new HashSet<>());
            for (TableItem r : rule.getTableItems()) {
                if (!terminals.contains(r.getName())) {
                    result.put(r, new HashSet<>());
                } else {
                    result.put(r, new HashSet<>(Set.of(r)));
                }
            }
        }
        boolean someChanged = true;
        while (someChanged) {
            someChanged = false;
            for (Rule rule : rules) {
                Set<TableItem> cur = result.get(rule.getTableItem());
                someChanged |= cur.addAll(first(rule.getTableItems(), result));
            }
        }
        return result;
    }

    public Set<TableItem> first(List<TableItem> alpha, Map<TableItem, Set<TableItem>> firstSets) {
        if (alpha.size() == 0) {
            return Set.of(EPSILON);
        }
        Set<TableItem> res = new HashSet<>();
        for (TableItem node : alpha) {
            if (firstSets.get(node).stream().noneMatch(TableItem::isEpsilon)) {
                res = StreamEx.of(res).filter(TableItem::notIsEpsilon).toSet();
                res.addAll(firstSets.get(node));
                break;
            } else {
                res.addAll(firstSets.get(node));
            }
        }
        return res;
    }

    public Set<ItemSet> createItemSet(Map<String, List<Rule>> rules, Set<TableItem> tokens, Rule rule) {
        ItemSet itemSet = new ItemSet();
        itemSet.addRule(rule.copyAndPointInStart());
        itemSet.expandSet(rules);

        Set<ItemSet> itemSets = new HashSet<>(Set.of(itemSet));

        Queue<ItemSet> queue = new ArrayDeque<>(Collections.singletonList(itemSet));
        int iteration = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                ItemSet tempItemSet = queue.poll();
                for (String token : tokens.stream().map(TableItem::getName).collect(Collectors.toList())) {
                    ItemSet modifiedItemSet = tempItemSet.apply(token, rules);
                    if (modifiedItemSet.getRulesSet().size() == 0) {
                        continue;
                    }
                    if (!itemSets.contains(modifiedItemSet)) {
                        itemSets.add(modifiedItemSet);
                        queue.add(modifiedItemSet);
                        modifiedItemSet.setId(iteration++);
                        table.put(new Pair<>(tempItemSet, token), modifiedItemSet);
                    } else {
                        ItemSet inSet = itemSets.stream().filter(k -> k.equals(modifiedItemSet)).findAny().get();
                        if (!table.containsKey(new Pair<>(tempItemSet, token))) {
                            table.put(new Pair<>(tempItemSet, token), inSet);
                        }
                    }
                }
            }
        }

        return itemSets;
    }

    public static Map<TableItem, Set<TableItem>> createFollow(Map<TableItem, Set<TableItem>> firstSets, Set<Rule> rules, Set<String> terminals) {
        Map<TableItem, Set<TableItem>> result = new HashMap<>();
        List<Rule> sorted = StreamEx.of(rules)
                .sorted(Comparator.comparing(Rule::getId))
                .toList();

        for (Rule rule : rules) {
            result.put(rule.getTableItem(), new HashSet<>());
        }
        result.get(sorted.get(0).getTableItem()).add(END);
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Rule rule : rules) {
                for (int i = 0; i < rule.getTokens().size(); i++) {
                    if (!terminals.contains(rule.getTokens().get(i))) {
                        Set<TableItem> cur = result.get(rule.getTableItems().get(i));
                        if (i + 1 == rule.getTokens().size()) {
                            changed |= cur.addAll(result.get(rule.getTableItem()));
                        } else {
                            changed |= cur.addAll(
                                    StreamEx.of(firstSets.get(rule.getTableItems().get(i + 1)))
                                            .filter(TableItem::notIsEpsilon)
                                            .toList()
                            );
                            if (firstSets.get(rule.getTableItems().get(i + 1)).contains(EPSILON)) {
                                changed |= cur.addAll(result.get(rule.getTableItem()));
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    public Set<Rule> createTranslationTable(Set<ItemSet> itemSets, Rule startRule) {
        List<ItemSet> sortedSets = StreamEx.of(itemSets).sorted(Comparator.comparing(ItemSet::getId)).toList();

        String tmp = startRule.getTokens().get(0);
        Rule rule = new Rule(new TableItem(startRule.getName()),
                List.of(new TableItem(tmp, table.get(new Pair<>(sortedSets.get(0), tmp)).getId())),
                0, 0, "", "");

        Set<Rule> rulesSet = new HashSet<>(Arrays.asList(rule));
        int ruleId = 1;

        for (ItemSet set : sortedSets) {
            List<Rule> rules = StreamEx.of(set.getRulesSet())
                    .filter(r -> r.getId() != 0 && r.getPoint() == 0)
                    .toList();
            for (Rule r : rules) {
                TableItem name = new TableItem(r.getName(), table.get(new Pair<>(set, r.getName())).getId());
                List<TableItem> tokens = new ArrayList<>();
                ItemSet curSet = set;
                for (String curString : r.getTokens()) {
                    ItemSet nextSet = table.get(new Pair<>(curSet, curString));
                    tokens.add(new TableItem(curString, nextSet.getId()));
                    curSet = nextSet;
                }
                rulesSet.add(new Rule(name, tokens, 0, ruleId++, "", ""));
            }

        }
        return rulesSet;
    }
}
