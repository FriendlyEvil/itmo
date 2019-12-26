package antlr;

import lombok.Getter;

public class Terminal extends Term {
    @Getter
    private String regexpr;

    public Terminal(String name, String regexpr) {
        super(name);
        this.regexpr = regexpr;
    }
}
