package doman.generated.logic;

import java.util.List;

public class Or implements Logic {
    private Logic first;
    private Logic second;

    public Or(Logic first, Logic second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toStringRecursive() {
        return "(" + first.toStringRecursive() + " or " + second.toStringRecursive() + ")";
    }

    @Override
    public String toString() {
        return "Or";
    }

    @Override
    public List<Logic> getSubTree() {
        return List.of(first, second);
    }

}
