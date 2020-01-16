package doman;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableItem {
    private String name;
    private int toId;

    public TableItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + toId;
    }

    public static final TableItem EPSILON = new TableItem("&epsilon");

    public boolean isEpsilon() {
        return EPSILON.name.equals(name);
    }

    public boolean notIsEpsilon() {
        return !isEpsilon();
    }
}
