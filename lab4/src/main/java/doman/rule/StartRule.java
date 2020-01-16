package doman.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class StartRule {
    @Getter
    private Rule rule;
    @Getter
    private String returnType;
    @Getter
    private String code;
}
