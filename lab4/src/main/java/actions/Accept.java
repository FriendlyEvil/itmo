package actions;

import lombok.Data;

@Data
public class Accept implements Action {
    private static final String template = "new Accept()";
    @Override
    public String generateNewObject() {
        return template;
    }
}
