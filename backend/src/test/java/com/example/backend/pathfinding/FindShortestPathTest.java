package com.example.backend.pathfinding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


class FindShortestPathTest {

    @Test
    void findShortestPath_returnCorrectlyShortestPathADEBC() {
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

        FindShortestPath path = new FindShortestPath(graph, new Vertex("A", 0), new ArrayList<>(), new ArrayList<>());
        List<Vertex> shortestPath = path.findShortestPath();
        List<String> actual = shortestPath.stream().map(vertex -> vertex.getName()).collect(Collectors.toList());

        Assertions.assertEquals(List.of("A", "D", "E", "B", "C"),actual);
    }


    @Test
    void findShortestPath_returnCorrectlyShortestPathABDEFC() {
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addDirectedEdge(new Edge("A", "B", 2));
        graph.addDirectedEdge(new Edge("A", "D", 8));
        graph.addDirectedEdge(new Edge("B", "A", 2));
        graph.addDirectedEdge(new Edge("B", "D", 5));
        graph.addDirectedEdge(new Edge("B", "E", 6));
        graph.addDirectedEdge(new Edge("C", "E", 9));
        graph.addDirectedEdge(new Edge("C", "F", 3));
        graph.addDirectedEdge(new Edge("D", "A", 8));
        graph.addDirectedEdge(new Edge("D", "B", 5));
        graph.addDirectedEdge(new Edge("D", "E", 3));
        graph.addDirectedEdge(new Edge("D", "F", 2));
        graph.addDirectedEdge(new Edge("E", "B", 6));
        graph.addDirectedEdge(new Edge("E", "C", 9));
        graph.addDirectedEdge(new Edge("E", "D", 3));
        graph.addDirectedEdge(new Edge("E", "F", 1));
        graph.addDirectedEdge(new Edge("F", "C", 3));
        graph.addDirectedEdge(new Edge("F", "D", 2));
        graph.addDirectedEdge(new Edge("F", "E", 1));

        FindShortestPath path = new FindShortestPath(graph, new Vertex("A", 0), new ArrayList<>(), new ArrayList<>());
        List<Vertex> shortestPath = path.findShortestPath();
        List<String> actual = shortestPath.stream().map(vertex -> vertex.getName()).collect(Collectors.toList());

        Assertions.assertEquals(List.of("A", "B", "D", "E", "F", "C"),actual);
    }

    @Test
    void findShortestPath_when2NeighborsHaveSameDistanceToSourceThenCorrectlyShortestPathADEBC() {
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");


        graph.addDirectedEdge(new Edge("A", "B", 6));
        graph.addDirectedEdge(new Edge("A", "D", 6));
        graph.addDirectedEdge(new Edge("B", "A", 6));
        graph.addDirectedEdge(new Edge("B", "C", 5));
        graph.addDirectedEdge(new Edge("B", "D", 2));
        graph.addDirectedEdge(new Edge("B", "E", 2));
        graph.addDirectedEdge(new Edge("C", "B", 5));
        graph.addDirectedEdge(new Edge("C", "E", 5));
        graph.addDirectedEdge(new Edge("D", "A", 6));
        graph.addDirectedEdge(new Edge("D", "B", 2));
        graph.addDirectedEdge(new Edge("D", "E", 1));
        graph.addDirectedEdge(new Edge("E", "B", 2));
        graph.addDirectedEdge(new Edge("E", "C", 5));
        graph.addDirectedEdge(new Edge("E", "D", 1));

        FindShortestPath path = new FindShortestPath(graph, new Vertex("A", 0), new ArrayList<>(), new ArrayList<>());
        List<Vertex> shortestPath = path.findShortestPath();
        List<String> actual = shortestPath.stream().map(vertex -> vertex.getName()).collect(Collectors.toList());

        Assertions.assertEquals(List.of("A", "D", "B", "E", "C"),actual);
    }
}