package com.example.backend.pathfinding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


class FindShortestPathTest {

    @Test
    void findShortestPath_returnADEBC() {
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");


        graph.addNonDirectedEdge(new Edge("A", "B", 6));
        graph.addNonDirectedEdge(new Edge("A", "D", 1));
        graph.addNonDirectedEdge(new Edge("D", "B", 2));
        graph.addNonDirectedEdge(new Edge("D", "E", 1));
        graph.addNonDirectedEdge(new Edge("B", "E", 2));
        graph.addNonDirectedEdge(new Edge("B", "C", 5));
        graph.addNonDirectedEdge(new Edge("E", "C", 5));

        FindShortestPath path = new FindShortestPath(graph, new Vertex("A", 0, ""), new ArrayList<>(), new ArrayList<>());
        List<Vertex> shortestPath = path.findShortestPath();
        List<String> expected = shortestPath.stream().map(vertex -> vertex.getName()).collect(Collectors.toList());

        Assertions.assertEquals(expected,List.of("A", "D", "E", "B", "C"));
    }


    @Test
    void findShortestPath_returnABDEFC() {
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addNonDirectedEdge(new Edge("A", "B", 2));
        graph.addNonDirectedEdge(new Edge("A", "D", 8));
        graph.addNonDirectedEdge(new Edge("B", "D", 5));
        graph.addNonDirectedEdge(new Edge("B", "E", 6));
        graph.addNonDirectedEdge(new Edge("D", "E", 3));
        graph.addNonDirectedEdge(new Edge("E", "F", 1));
        graph.addNonDirectedEdge(new Edge("D", "F", 2));
        graph.addNonDirectedEdge(new Edge("E", "C", 9));
        graph.addNonDirectedEdge(new Edge("F", "C", 3));

        FindShortestPath path = new FindShortestPath(graph, new Vertex("A", 0, ""), new ArrayList<>(), new ArrayList<>());
        List<Vertex> shortestPath = path.findShortestPath();
        List<String> expected = shortestPath.stream().map(vertex -> vertex.getName()).collect(Collectors.toList());

        Assertions.assertEquals(expected,List.of("A", "B", "D", "E", "F", "C"));
    }
}