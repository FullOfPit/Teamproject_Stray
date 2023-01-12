package com.example.backend.pathfinding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class DistanceMatrixTest {

    @Test
    void toGraph_WhenSymmetricDistanceMatrixThenReturnGraphCorrectly() {
        //given
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");


        graph.addDirectedEdge(new Edge("A", "B", 6));
        graph.addDirectedEdge(new Edge("A", "D", 1));
        graph.addDirectedEdge(new Edge("B", "A", 6));
        graph.addDirectedEdge(new Edge("B", "C", 5));
        graph.addDirectedEdge(new Edge("B", "D", 2));
        graph.addDirectedEdge(new Edge("B", "E", 2));
        graph.addDirectedEdge(new Edge("C", "B", 5));
        graph.addDirectedEdge(new Edge("C", "E", 5));
        graph.addDirectedEdge(new Edge("D", "A", 1));
        graph.addDirectedEdge(new Edge("D", "B", 2));
        graph.addDirectedEdge(new Edge("D", "E", 1));
        graph.addDirectedEdge(new Edge("E", "B", 2));
        graph.addDirectedEdge(new Edge("E", "C", 5));
        graph.addDirectedEdge(new Edge("E", "D", 1));

        //when
        List<String> vertexNames = List.of("A","B","C","D","E");
        List<List<Double>> matrix = new ArrayList<>();
        matrix.add(List.of(0.,6.,0.,1.,0.));
        matrix.add(List.of(6.,0.,5.,2.,2.));
        matrix.add(List.of(0.,5.,0.,0.,5.));
        matrix.add(List.of(1.,2.,0.,0.,1.));
        matrix.add(List.of(0.,2.,5.,1.,0.));

        //then
        DistanceMatrix distanceMatrix = new DistanceMatrix(vertexNames,matrix);
        Graph actual = distanceMatrix.toGraph();

        Assertions.assertEquals(graph,actual);
    }


    @Test
    void toGraph_WhenAsymmetricDistanceMatrixThenReturnGraphCorrectly() {
        //given
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addDirectedEdge(new Edge("A", "B", 3));
        graph.addDirectedEdge(new Edge("A", "C", 2));
        graph.addDirectedEdge(new Edge("B", "A", 6));
        graph.addDirectedEdge(new Edge("B", "C", 5));
        graph.addDirectedEdge(new Edge("B", "D", 2));
        graph.addDirectedEdge(new Edge("C", "B", 5));
        graph.addDirectedEdge(new Edge("D", "A", 1));
        graph.addDirectedEdge(new Edge("D", "C", 2));

        //when
        List<String> vertexNames = List.of("A","B","C","D");
        List<List<Double>> matrix = new ArrayList<>();
        matrix.add(List.of(0.,3.,2.,0.));
        matrix.add(List.of(6.,0.,5.,2.));
        matrix.add(List.of(0.,5.,0.,0.));
        matrix.add(List.of(1.,0.,2.,0.));

        //then
        DistanceMatrix distanceMatrix = new DistanceMatrix(vertexNames,matrix);
        Graph actual = distanceMatrix.toGraph();

        Assertions.assertEquals(graph,actual);
    }

    @Test
    void toGraph_WhenNotSquareDistanceMatrixThenReturnGraphCorrectly() {
        //given
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");

        graph.addDirectedEdge(new Edge("A", "B", 3));
        graph.addDirectedEdge(new Edge("A", "C", 2));
        graph.addDirectedEdge(new Edge("B", "A", 6));
        graph.addDirectedEdge(new Edge("B", "C", 5));
        graph.addDirectedEdge(new Edge("B", "D", 2));

        //when
        List<String> vertexNames = List.of("A","B","C","D");
        List<List<Double>> matrix = new ArrayList<>();
        matrix.add(List.of(0.,3.,2.,0.));
        matrix.add(List.of(6.,0.,5.,2.));

        //then
        DistanceMatrix distanceMatrix = new DistanceMatrix(vertexNames,matrix);
        Graph actual = distanceMatrix.toGraph();

        Assertions.assertEquals(graph,actual);
    }
}