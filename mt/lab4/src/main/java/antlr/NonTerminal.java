package antlr;

import doman.rule.Rule;
import doman.rule.StartRule;
import lombok.Getter;
import one.util.streamex.StreamEx;
import utils.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NonTerminal extends Term {
    @Getter
    private List<String> tokens;
    @Getter
    private String code;
    @Getter
    private ReturnType returnType;

    private List<NonTerminal> nonTerminals;

    public NonTerminal(String name, ReturnType returnType, List<String> tokens, String code) {
        super(name);
        this.tokens = tokens;
        Collections.reverse(tokens);
        this.returnType = returnType;
        this.code = code;
        this.nonTerminals = new ArrayList<>(Arrays.asList(this));
    }


    public NonTerminal(String name, ReturnType returnType, List<String> tokens, List<Pair<List<String>, String>> tokenList, String code) {
        this(name, returnType, tokens, code);
        this.nonTerminals.addAll(StreamEx.of(tokenList).map(tok -> new NonTerminal(name, returnType, tok.getFirst(), tok.getSecond())).toList());
        Collections.reverse(this.nonTerminals);
    }

    public List<StartRule> getOnlyRules() {
        return nonTerminals.stream().map(t -> new StartRule(new Rule(t.getName(), t.getTokens(), t.getReturnType(), t.getCode()), "", ""))
                .collect(Collectors.toList());
    }
}
