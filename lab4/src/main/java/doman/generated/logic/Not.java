package doman.generated.logic;

import java.util.List;

public class Not implements Logic {
    private Logic logic;

    public Not(Logic logic) {
        this.logic = logic;
    }

    @Override
    public String toStringRecursive() {
        return "(not " + logic.toStringRecursive() + ")";
    }

    @Override
    public String toString() {
        return "Not";
    }

    @Override
    public List<Logic> getSubTree() {
        return List.of(logic);
    }
}
