package doman.rule;

import antlr.ReturnType;
import doman.TableItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import one.util.streamex.StreamEx;

import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Rule {
    private TableItem name;
    private List<TableItem> tokens;
    private int point;
    private int id;

    @EqualsAndHashCode.Exclude
    private String returnType;
    @EqualsAndHashCode.Exclude
    private String code;

    public Rule(Rule rule, int id) {
        this.name = new TableItem(rule.getName());
        this.tokens = StreamEx.of(rule.getTokens()).map(TableItem::new).toList();
        this.point = rule.getPoint();
        this.id = id;
        this.returnType = rule.returnType;
        this.code = rule.code;
    }

    public Rule(String name, List<String> tokens) {
        this.name = new TableItem(name);
        this.tokens = StreamEx.of(tokens).map(TableItem::new).toList();
        this.point = 0;
    }

    public Rule(String name, List<String> tokens, ReturnType returnType, String code) {
        this.name = new TableItem(name);
        this.tokens = StreamEx.of(tokens).map(TableItem::new).toList();
        this.point = 0;
        this.returnType = returnType.getType();
        this.code = code;
    }

    public ProductionRule toProductionRule() {
        return new ProductionRule(getName(), getTokens(), getId());
    }

    public List<String> getTokens() {
        return StreamEx.of(tokens).map(TableItem::getName).toList();
    }

    public List<TableItem> getTableItems() {
        return tokens;
    }

    public String getName() {
        return name.getName();
    }

    public TableItem getTableItem() {
        return name;
    }

    public Rule copyAndPointInStart() {
        return copy(0);
    }

    public Rule copyAndIncrementPointer() {
        return copy(point + 1);
    }

    public boolean pointInEnd() {
        return point >= tokens.size();
    }

    public boolean pointNotInEnd() {
        return !pointInEnd();
    }

    private Rule copy(int pointer) {
        return new Rule(name, tokens, pointer, id, returnType, code);
    }

    public String getPointedElement() {
        return StreamEx.of(tokens).map(TableItem::getName).toList().get(point);
    }

    public boolean isPointedElement(String name) {
        return name.equals(getPointedElement());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name + " -> ");
        for (int i = 0; i < tokens.size(); i++) {
            if (i == point) {
                builder.append(' ');
                builder.append('•');
            }
            builder.append(' ');
            builder.append(tokens.get(i));
        }
        if (point == tokens.size()) {
            builder.append(' ');
            builder.append('•');
        }
        return builder.toString();
    }
}
