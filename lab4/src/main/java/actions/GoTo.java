package actions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoTo implements Action {
    int to;

    private static final String template = "new GoTo(%d)";
    @Override
    public String generateNewObject() {
        return String.format(template, to);
    }
}
