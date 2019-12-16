import java.util.Arrays;
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
    }

    public void show() {
        Printer.printNode(getBinaryTree());
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
        return node;
    }
}
