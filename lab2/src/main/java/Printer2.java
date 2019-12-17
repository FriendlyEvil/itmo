import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;

public class Printer2 extends JFrame {
    private static int WIDTH = 30;
    private static int HEIGHT = 20;

    private static void recursiveDraw(mxGraph graph, Object defaultParent, Tree tree, Object parent) {
        for (Tree child : tree.children) {
            Object childVertex = graph.insertVertex(defaultParent, null, child.node, 0, 0, WIDTH, HEIGHT);
            graph.insertEdge(defaultParent, null, null, parent, childVertex);
            recursiveDraw(graph, defaultParent, child, childVertex);
        }
    }

    public static void show(Tree tree) {
        Printer2 frame = new Printer2();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

        frame.draw(tree);

        frame.setVisible(true);
    }

    private void draw(Tree tree) {
        mxGraph graph = new mxGraph();
        Object defaultParent = graph.getDefaultParent();

        Object rootVertex = graph.insertVertex(defaultParent, null, tree.node, 0, 0, WIDTH, HEIGHT);
        recursiveDraw(graph, defaultParent, tree, rootVertex);

        new mxCompactTreeLayout(graph, false).execute(defaultParent);
        getContentPane().add(new mxGraphComponent(graph));
    }
}
