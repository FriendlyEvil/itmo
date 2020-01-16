package doman.generated.pascal;

import java.util.List;

public class Vars {
    private List<String> vars;
    private String type;

    public Vars(List<String> vars, String type) {
        this.vars = vars;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Vars{" +
                "vars=" + vars +
                ", type='" + type + '\'' +
                '}' + '\n';
    }
}
