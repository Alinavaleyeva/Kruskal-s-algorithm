package com.kruskal.shapeview;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class NodeView extends Circle {
    private int idNumber; // Удален модификатор final
    private final Text text;
    private final List<EdgeView> incidentEdgeIdList = new ArrayList<>();

    public int getIdNumber() {
        return idNumber;
    }

    public NodeView(double x, double y, double radius, Paint paint, int idNumber) {
        super(x, y, radius, paint);
        this.idNumber = idNumber;
        this.text = new Text(x-7, y+7, Integer.toString(idNumber));
        this.text.setStyle("-fx-font-size: 25");
    }

    public Text getText() {
        return text;
    }

    public void addIncidentEdgeId(EdgeView edge) {
        incidentEdgeIdList.add(edge);
    }

    public boolean hasIncidentEdge(EdgeView edge) {
        return incidentEdgeIdList.contains(edge);
    }

    public void removeEdge(EdgeView edge) {
        incidentEdgeIdList.remove(edge);
    }

    public boolean isStartVertex(EdgeView edge) {
        return edge.getStartNode().equals(this);
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
        this.text.setText(String.valueOf(idNumber)); // Обновляем текстовое представление идентификатора
    }
}
