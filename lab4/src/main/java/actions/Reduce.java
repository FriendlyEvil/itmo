package actions;

import lombok.AllArgsConstructor;
import lombok.Data;
import doman.rule.ProductionRule;

@Data
@AllArgsConstructor
public class Reduce implements Action {
    String action;
    ProductionRule rule;

    private static final String template = "new Reduce(\"%s\", %s)";
    @Override
    public String generateNewObject() {
        return String.format(template, action, rule.generateNewObject());
    }
}
