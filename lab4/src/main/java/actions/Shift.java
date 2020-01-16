package actions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Shift implements Action {
    int to;

    private static final String template = "new Shift(%d)";
    @Override
    public String generateNewObject() {
        return String.format(template, to);
    }
}
