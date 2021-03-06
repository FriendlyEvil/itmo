package doman.generated.logic;

import java.util.List;

public class Xor implements Logic {
    private Logic first;
    private Logic second;

    public Xor(Logic first, Logic second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toStringRecursive() {
        return "(" + first.toStringRecursive() + " xor " + second.toStringRecursive() + ")";
    }

    @Override
    public String toString() {
        return "Xor";
    }

    @Override
    public List<Logic> getSubTree() {
        return List.of(first, second);
    }
}
