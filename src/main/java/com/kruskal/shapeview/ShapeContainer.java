package com.kruskal.shapeview;

import com.kruskal.graph.Edge;
import com.kruskal.graph.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ShapeContainer {
    private final List<NodeView> nodeViewList = new ArrayList<>();
    private final List<EdgeView> edgeViewList = new ArrayList<>();
    private final Pane pane;


    public ShapeContainer(Pane pane) {
        this.pane = pane;
    }

    public void createNode(double xCoordinate, double yCoordinate, double radius, Color color, int nodeId) {
        NodeView node = new NodeView(xCoordinate, yCoordinate, radius, color, nodeId);
        nodeViewList.add(node);
        pane.getChildren().addAll(node, node.getText());
        redrawGraph(); // Перерисовываем граф после добавления узла

    }

    public void createEdge(int startNodeId, int endNodeId, int weight, int edgeId) {
        EdgeView edgeView = new EdgeView(edgeId, weight);
        NodeView startNode = null, endNode = null;
        for (NodeView node : nodeViewList) {
            if (node.getIdNumber() == startNodeId) {
                startNode = node;
                edgeView.setStartX(node.getCenterX());
                edgeView.setStartY(node.getCenterY());
                node.addIncidentEdgeId(edgeView);
            } else if (node.getIdNumber() == endNodeId) {
                endNode = node;
                edgeView.setEndX(node.getCenterX());
                edgeView.setEndY(node.getCenterY());
                node.addIncidentEdgeId(edgeView);
            }
        }
        edgeView.setAdjacentNodes(startNode, endNode);
        edgeView.setStroke(Color.BLACK);
        edgeView.setStrokeWidth(5d);
        edgeView.getTextFlow().setLayoutX((edgeView.getEndX() + edgeView.getStartX()) / 2 - 10);
        edgeView.getTextFlow().setLayoutY((edgeView.getEndY() + edgeView.getStartY()) / 2 - 10);
        edgeView.getWeightText().setX((edgeView.getEndX() + edgeView.getStartX()) / 2-2.5); // Позиция X для текста веса
        edgeView.getWeightText().setY((edgeView.getEndY() + edgeView.getStartY()) / 2+2); // Позиция Y для текста веса

        // Добавление ребра, текста веса и текстового потока на панель
        pane.getChildren().addAll(edgeView, edgeView.getTextFlow(), edgeView.getWeightText());

        edgeViewList.add(edgeView);
        redrawGraph(); // Перерисовываем граф после добавления ребра
    }

    public void removeNode(int nodeId) {
        for (NodeView node : nodeViewList) {
            if (nodeId == node.getIdNumber()) {
                List<EdgeView> needToBeDeletedEdge = new ArrayList<>();
                for (EdgeView edge : edgeViewList) {
                    if (node.hasIncidentEdge(edge)) {
                        if (!node.equals(edge.getStartNode())) {
                            edge.getStartNode().removeEdge(edge);
                        } else {
                            edge.getEndNode().removeEdge(edge);
                        }
                        pane.getChildren().remove(edge.getTextFlow());
                        pane.getChildren().remove(edge);
                        needToBeDeletedEdge.add(edge);
                        node.removeEdge(edge);
                        // Удаление текста числа ребра
                        pane.getChildren().remove(edge.getWeightText());
                    }
                }
                for (EdgeView edge : needToBeDeletedEdge) {
                    if (edgeViewList.contains(edge)) {
                        edgeViewList.remove(edge);
                    }
                }
                nodeViewList.remove(node);
                pane.getChildren().remove(node.getText());
                pane.getChildren().remove(node);
                break;
            }
        }
    }

//    public void removeEdge(int edgeId) {
//        for (EdgeView edge : edgeViewList) {
//            if (edge.getIdNumber() == edgeId) {
//                edge.getStartNode().removeEdge(edge);
//                edge.getEndNode().removeEdge(edge);
//                edgeViewList.remove(edge);
//                pane.getChildren().remove(edge.getTextFlow());
//                pane.getChildren().remove(edge);
//                break;
//            }
//        }
//    }

    public void removeEdge(int edgeId) {
        for (EdgeView edge : edgeViewList) {
            if (edge.getIdNumber() == edgeId) {
                edge.getStartNode().removeEdge(edge);
                edge.getEndNode().removeEdge(edge);
                edgeViewList.remove(edge);
                pane.getChildren().remove(edge.getTextFlow());
                pane.getChildren().remove(edge);
                // Удаление текста числа ребра
                pane.getChildren().remove(edge.getWeightText());
                break;
            }
        }
    }
    public void redrawGraph() {
        // Очистка панели
        pane.getChildren().clear();

        // Добавляем все рёбра
        for (EdgeView edge : edgeViewList) {
            pane.getChildren().addAll(edge, edge.getTextFlow());
        }

        // Добавляем все узлы
        for (NodeView node : nodeViewList) {
            pane.getChildren().addAll(node, node.getText());
        }

        // Добавляем веса рёбер после отрисовки всех рёбер и узлов
        for (EdgeView edge : edgeViewList) {
            pane.getChildren().add(edge.getWeightText());
        }
    }


    public void clear() {
        nodeViewList.clear();
        edgeViewList.clear();
        pane.getChildren().clear();
    }

    public void replaceNode(double x, double y, int objectId) {
        for (NodeView node : nodeViewList) {
            if (node.getIdNumber() == objectId) {
                node.setCenterX(x);
                node.setCenterY(y);
                node.getText().setX(x);
                node.getText().setY(y);
                for (EdgeView edge : edgeViewList) {
                    if (node.hasIncidentEdge(edge) && node.isStartVertex(edge)) {
                        edge.setStartX(x);
                        edge.setStartY(y);
                        edge.getTextFlow().setLayoutX((edge.getEndX() + edge.getStartX()) / 2 - 10);
                        edge.getTextFlow().setLayoutY((edge.getEndY() + edge.getStartY()) / 2 - 10);
                        edge.getWeightText().setX((edge.getEndX() + edge.getStartX()) / 2); // Обновление X для текста веса
                        edge.getWeightText().setY((edge.getEndY() + edge.getStartY()) / 2); // Обновление Y для текста веса
                    } else if (node.hasIncidentEdge(edge)) {
                        edge.setEndX(x);
                        edge.setEndY(y);
                        edge.getTextFlow().setLayoutX((edge.getEndX() + edge.getStartX()) / 2 - 10);
                        edge.getTextFlow().setLayoutY((edge.getEndY() + edge.getStartY()) / 2 - 10);
                        edge.getWeightText().setX((edge.getEndX() + edge.getStartX()) / 2); // Обновление X для текста веса
                        edge.getWeightText().setY((edge.getEndY() + edge.getStartY()) / 2); // Обновление Y для текста веса
                    }
                }
                break;
            }
        }
    }

    public void colorEdge(int edgeId, Color color) {
        for (EdgeView edge : edgeViewList) {
            if (edge.getIdNumber() == edgeId) {
                edge.setStroke(color);
            }
        }
    }
}
