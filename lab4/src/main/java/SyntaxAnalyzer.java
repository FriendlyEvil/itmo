import antlr.NonTerminal;
import antlr.Rule;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class SyntaxAnalyzer {
    public void createItemsSet(Map<String, List<Rule>> rules, NonTerminal start) {
        Map<Set<Rule>, Integer> itemSetMap = new HashMap<>();
        List<Set<Rule>> itemSetList = new ArrayList<>();
        List<Map<String, Integer>> translationTable = new ArrayList<>();

        Queue<Pair> q = new ArrayDeque<>();
        q.add(new Pair(start.getRules(), -1));

        while (!q.isEmpty()) {
            Pair temp = q.poll();
            Set<Rule> itemSet = new HashSet<>();
            for (Rule rule : temp.nonTerminal) {
                addRule(rules, rule, itemSet);
            }

            if (!itemSetMap.containsKey(itemSet)) {
                itemSetMap.put(itemSet, itemSet.size());
                itemSetList.add(itemSet);
                translationTable.add(new HashMap<>());


                Map<String, List<Rule>> collect = itemSet.stream().filter(i -> i.getTokens().size() > i.getPointer()).collect(Collectors.groupingBy(Rule::getName));
                for (Map.Entry<String, List<Rule>> e : collect.entrySet()) {
                    List<Rule> rules1 = e.getValue().stream().map(i -> i.copy(i.getPointer() + 1)).collect(Collectors.toList());
                    q.add(new Pair(rules1, itemSetList.size() - 1));
                }
            }

            Rule rule = temp.nonTerminal.get(0);
            int num = temp.num;
            if (num != -1) {
                translationTable.get(num).put(rule.getTokens().get(rule.getPointer() - 1), itemSetMap.get(itemSet));
            }
        }
        System.out.println("end");
    }

    private void addRule(Map<String, List<Rule>> rules, Rule rule, Set<Rule> itemSet) {
        if (itemSet.contains(rule)) {
            return;
        }
        itemSet.add(rule);
        if (rule.getPointer() >= rule.getTokens().size()) {
            return;
        }
        if (rules.get(rule.get()) != null) {
            for (Rule pointed : rules.get(rule.get())) {
                addRule(rules, pointed.copy(), itemSet);
            }
        }
    }

    @AllArgsConstructor
    class Pair {
        List<Rule> nonTerminal;
        int num;
    }
}
