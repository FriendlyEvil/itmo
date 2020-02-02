package doman;

import antlr.NonTerminal;
import antlr.ParserInput;
import antlr.Term;
import antlr.Terminal;
import doman.rule.StartRule;
import lombok.AllArgsConstructor;
import one.util.streamex.StreamEx;

import java.util.*;

@AllArgsConstructor
public class Input {
    ParserInput input;

    public String getHeader() {
        if (input.getHeader().length() == 0) {
            return "";
        }
        return input.getHeader().substring(1, input.getHeader().length() - 1);
    }

    public Set<TableItem> getTokens() {
        Set<String> ans = new HashSet<>();
        for (Term rule : input.getRules()) {
            ans.add(rule.getName());
        }
        ans.add(input.getStart().getName());
        return StreamEx.of(ans).map(TableItem::new).toSet();
    }

    public List<Terminal> getTerminals() {
        List<Terminal> res = new ArrayList<>();
        for (Term term: input.getRules()) {
            if (term instanceof Terminal) {
                res.add((Terminal) term);
            }
        }
        Collections.reverse(res);
        return res;
    }

    public List<StartRule> getOnlyRules() {
        List<StartRule> res = new ArrayList<>();
        for (Term term: input.getRules()) {
            if (term instanceof NonTerminal) {
                List<StartRule> nonTerminalRules = ((NonTerminal) term).getOnlyRules();
                res.addAll(nonTerminalRules);
            }
        }
        res.addAll(input.getStart().getOnlyRules());
        Collections.reverse(res);
        return res;
    }
}
