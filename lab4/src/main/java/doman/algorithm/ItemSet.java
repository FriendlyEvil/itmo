package doman.algorithm;

import doman.rule.Rule;
import lombok.*;
import one.util.streamex.StreamEx;

import java.util.*;

@AllArgsConstructor
@EqualsAndHashCode
public class ItemSet {
    @Getter
    @Setter
    @EqualsAndHashCode.Exclude
    private int id;
    Set<Rule> setRules;
    @EqualsAndHashCode.Exclude
    private boolean somethingChange;

    public ItemSet(Set<Rule> setRules) {
        this.setRules = setRules;
    }

    public ItemSet() {
        this.setRules = new HashSet<>();
    }

    public void addRule(Rule rule) {
        somethingChange |= setRules.add(rule);
    }

    public Set<Rule> getRulesSet() {
        return new HashSet<>(setRules);
    }

    public ItemSet apply(String node, Map<String, List<Rule>> rules) {
        ItemSet itemSet = new ItemSet(StreamEx.of(setRules)
                .filter(Rule::pointNotInEnd)
                .filter(rule -> rule.isPointedElement(node))
                .map(Rule::copyAndIncrementPointer)
                .toSet());
        itemSet.expandSet(rules);
        return itemSet;
    }

    public void expandSet(Map<String, List<Rule>> rules) {
        somethingChange = true;
        while (somethingChange) {
            somethingChange = false;
            StreamEx.of(getRulesSet())
                    .filter(Rule::pointNotInEnd)
                    .map(Rule::getPointedElement)
                    .map(rules::get)
                    .nonNull()
                    .forEach(rule -> StreamEx.of(rule)
                            .map(Rule::copyAndPointInStart)
                            .forEach(this::addRule));
        }
    }

    @Override
    public String toString() {
        return id + ": " + StreamEx.of(setRules).map(Rule::toString).joining(", ");
    }
}
