package com.kruskal.kruskalalgorithm;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kruskal.graph.EdgeData;
import com.kruskal.graph.Graph;

public class Kruskal {
    private final List<StepMessage> steps;
    private int currentStep;
    private final List<EdgeData> edges;
    private final List<Set<Integer>> connectedNodes;
    private int edgeNumber;
    private int edgesWeightSum;

    public Kruskal(Graph graph){
        steps = new ArrayList<StepMessage>();
        currentStep = 0;
        Set<Integer> nodes = graph.getNodesId();
        edges = graph.getEdgesData();

        Comparator<EdgeData> comparator = new Comparator<EdgeData>() {
            public int compare(EdgeData first, EdgeData second){
                return Integer.valueOf(first.getWeight()).compareTo(Integer.valueOf(second.getWeight()));
            }
        };
        edges.sort(comparator);

        connectedNodes = new ArrayList<Set<Integer>>();
        for (Integer node: nodes) {
            Set<Integer> newSet = new HashSet<Integer>();
            newSet.add(node);
            connectedNodes.add(newSet);
        }

        edgeNumber = 0;
        edgesWeightSum = 0;
    }

    public StepMessage makeStep(){
        if(currentStep < edgeNumber){
            currentStep++;
            edgesWeightSum += steps.get(currentStep - 1).getWeightShift();
            return steps.get(currentStep - 1);
        }

        if(edgeNumber == edges.size()){
            return new StepMessage(StepMessageType.ALGORITHMENDED);
        }

        if(connectedNodes.size() == 1){
            return new StepMessage(StepMessageType.ALGORITHMENDED);
        }

        EdgeData currentEdge = edges.get(edgeNumber);
        edgeNumber++;

        Set<Integer> firstNodeSet = null;
        Set<Integer> secondNodeSet = null;

        for (Set<Integer> set : connectedNodes) {
            if(firstNodeSet == null){
                if(set.contains(currentEdge.getFirstNodeId())){
                    firstNodeSet = set;
                }
            }   
            
            if(secondNodeSet == null){
                if(set.contains(currentEdge.getSecondNodeId())){
                    secondNodeSet = set;
                }
            }
        }

        if(firstNodeSet.equals(secondNodeSet)){
            steps.add(new StepMessage(currentEdge, StepMessageType.EDGEDECLINED, 0));
            currentStep++;
            return steps.get(currentStep - 1);
        }

        firstNodeSet.addAll(secondNodeSet);
        connectedNodes.remove(secondNodeSet);
        edgesWeightSum += currentEdge.getWeight();

        steps.add(new StepMessage(currentEdge, StepMessageType.EDGEADDED, currentEdge.getWeight()));
        currentStep++;
        return steps.get(currentStep - 1);
    }

    public StepMessage stepBack(){
        if(currentStep == 0){
            return null;
        }

        currentStep--;
        edgesWeightSum -= steps.get(currentStep).getWeightShift();
        return steps.get(currentStep);
    }

    public int getCurrentTreeWeight(){
        return edgesWeightSum;
    }


    public boolean isFinish() {
        // Алгоритм завершен, если все ребра обработаны и нет новых ребер для добавления
        return connectedNodes.size() != 1 && !edges.isEmpty();
    }
}
