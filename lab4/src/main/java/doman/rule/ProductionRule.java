package doman.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.util.List;

@AllArgsConstructor
@Value
public class ProductionRule {
    private String name;
    private List<String> tokens;
    private int id;

    private static final String template = "new ProductionRule(\"%s\", List.of(%s), %d)";

    public String generateNewObject() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            if (i != 0) {
                builder.append(", ");
            }
            builder.append('"').append(tokens.get(i)).append('"');
        }
        return String.format(template, name, builder.toString(), id);
    }

    public boolean equalsRule(Rule rule) {
        return name.equals(rule.getName()) && tokens.size() == rule.getTokens().size() &&
                IntStreamEx.range(tokens.size()).filter(i -> !tokens.get(i).equals(rule.getTokens().get(i))).count() == 0;
    }
}
