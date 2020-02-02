package antlr;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@AllArgsConstructor
public class ParserInput {
    private String header;
    private NonTerminal start;
    private List<Term> rules;
}
