import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tree {
    String node;
    List<Tree> children;

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
        children = Collections.emptyList();
    }

    public void show() {
        Printer.printNode(getBinaryTree());
    }

    public void show2() {
        Printer2.show(this);
    }

    private Printer.Node<Tree> getBinaryTree() {
        Printer.Node<Tree> nod = new Printer.Node<>(this);
        if (children != null) {
            if (children.size() >= 1) {
                nod.left = children.get(0).getBinaryTree();
            }
            if (children.size() >= 2) {
                nod.right = children.get(1).getBinaryTree();
            }
        }
        return nod;
    }

    @Override
    public String toString() {
        switch (node) {
            case "n":
                return "2";
            case "+":
            case "-":
            case "*":
            case "(":
            case ")":
                return node;
        }
        StringBuilder builder = new StringBuilder();
        for (Tree child : children) {
            builder.append(child.toString());
        }
        return builder.toString();
    }
}
