package antlr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class Rule {
    @Getter
    private String name;
    @Getter
    private List<String> tokens;
    @Setter
    @Getter
    private int pointer;

    public Rule copy() {
        return copy(0);
    }

    public Rule copy(int pointer) {
        return new Rule(name, tokens, pointer);
    }

    public String get() {
        return tokens.get(pointer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return pointer == rule.pointer &&
                name.equals(rule.name) &&
                tokens.equals(rule.tokens);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tokens, pointer);
    }
}
