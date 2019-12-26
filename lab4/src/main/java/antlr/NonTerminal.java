package antlr;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class NonTerminal extends Term {
    @Getter
    private List<List<String>> tokens;
    @Getter
    private String code;
    @Getter
    private ReturnType returnType;

    public NonTerminal(String name, ReturnType returnType, List<String> tokens, List<List<String>> tokenList, String code) {
        super(name);
        this.tokens = new ArrayList<>(Collections.singletonList(tokens));
        this.returnType = returnType;
        this.tokens.addAll(tokenList);
        this.code = code;
    }

    public List<Rule> getRules() {
        return tokens.stream().map(t -> new Rule(getName(), t, 0)).collect(Collectors.toList());
    }
}
