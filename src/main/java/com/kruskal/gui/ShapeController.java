package com.kruskal.gui;

import com.kruskal.kruskalalgorithm.StepMessage;
import com.kruskal.shapeview.ShapeContainer;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class ShapeController {
    private final ShapeContainer shapeContainer;


    public ShapeController(Pane pane) {
        this.shapeContainer = new ShapeContainer(pane);
    }


    public void clear() {
        shapeContainer.clear();
    }


    public void removeEdge(int edgeId) {
        shapeContainer.removeEdge(edgeId);
    }

    public void removeNode(int nodeId) {
        shapeContainer.removeNode(nodeId);
    }

    public void addEdge(int startNodeId, int endNodeId, int weight, int edgeId) {
        shapeContainer.createEdge(startNodeId, endNodeId, weight, edgeId);
    }

    public void addNode(double x, double y, int nodeId) {
        shapeContainer.createNode(x, y,
                30, Color.rgb(157, 0, 255), nodeId);
    }

    public void replaceNode(double x, double y, int objectId) {
        shapeContainer.replaceNode(x, y, objectId);
    }
    public void paintEdge(StepMessage stepMessage) {
        switch (stepMessage.getType()) {
            case EDGEADDED -> shapeContainer.colorEdge(stepMessage.getEdgeId(), Color.LIME);
            case EDGEDECLINED -> shapeContainer.colorEdge(stepMessage.getEdgeId(), Color.RED);
        }
    }

    public void paintEdgeDefault(StepMessage stepMessage) {
        shapeContainer.colorEdge(stepMessage.getEdgeId(), Color.rgb(0, 0, 0));
    }
}
