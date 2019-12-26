package antlr;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@AllArgsConstructor
public class ParserInput {
    private String header;
    private NonTerminal start;
    private List<Term> rules;

    public static void main(String[] args) {
        new ArrayList<>();
    }

    public Map<String, List<Rule>> getRuless() {
        Map<String, List<Rule>> res = new HashMap<>();
        for (Term term: rules) {
            if (term instanceof NonTerminal) {
                NonTerminal nonTerminal = (NonTerminal) term;
                res.put(nonTerminal.getName(), nonTerminal.getRules());
            }
        }
        return res;
    }
}
