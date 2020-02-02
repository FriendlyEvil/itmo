package doman.generated.logic;

import java.util.List;

public class And implements Logic {
    private Logic first;
    private Logic second;

    public And(Logic first, Logic second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toStringRecursive() {
        return "(" + first.toStringRecursive() + " and " + second.toStringRecursive() + ")";
    }

    @Override
    public String toString() {
        return "And";
    }

    @Override
    public List<Logic> getSubTree() {
        return List.of(first, second);
    }
}
