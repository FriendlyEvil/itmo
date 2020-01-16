package doman.generated.logic;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Visualizer extends JFrame {

    private static int WIDTH = 100;
    private static int HEIGHT = 50;

    private static void recursiveDraw(mxGraph graph, Object defaultParent, List<Logic> tree, Object parent) {
        for (Logic child : tree) {
            Object childVertex = graph.insertVertex(defaultParent, null, child.toString(), 0, 0, WIDTH, HEIGHT, "");
            graph.insertEdge(defaultParent, null, null, parent, childVertex);
            recursiveDraw(graph, defaultParent, child.getSubTree(), childVertex);
        }
    }

    public static void show(List<Logic> tree) {
        Visualizer frame = new Visualizer();
        frame.setSize(1920, 1080);

        frame.setFont(new Font("Courier", Font.BOLD,750));
        frame.draw(tree);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void draw(List<Logic> tree) {
        mxGraph graph = new mxGraph();
        Object defaultParent = graph.getDefaultParent();

        recursiveDraw(graph, defaultParent, tree, defaultParent);

        new mxCompactTreeLayout(graph, false).execute(defaultParent);
        getContentPane().add(new mxGraphComponent(graph));
    }
}
