package doman.generated.logic;

import java.util.Collections;
import java.util.List;

public class Var implements Logic {
    private String name;

    public Var(String name) {
        this.name = name;
    }

    @Override
    public String toStringRecursive() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public List<Logic> getSubTree() {
        return Collections.emptyList();
    }
}
