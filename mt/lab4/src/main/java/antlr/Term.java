package antlr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public abstract class Term {
    @Getter
    private String name;
}
