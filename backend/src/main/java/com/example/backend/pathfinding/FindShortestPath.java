package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Double.POSITIVE_INFINITY;

@Data
@AllArgsConstructor
public class FindShortestPath {
    private Graph graph;
    private Vertex source;
    private List<Vertex> visited;
    private List<Vertex> unvisited;


    public List<Vertex> findShortestPath() {
        initialize();

        while (!this.unvisited.isEmpty()) {
            Vertex evaluatedVertex = findVertexWithMinDistanceToSource();
            this.unvisited.remove(evaluatedVertex);
            this.visited.add(evaluatedVertex);

            Map<Vertex, Double> neighbors = findDistancesToUnvisitedNeighbors(evaluatedVertex);
            for (Vertex vertex : neighbors.keySet()) {
                double distance = neighbors.get(vertex);
                double alternativeDistanceToSource = evaluatedVertex.getDistanceToSource() + distance;
                if (alternativeDistanceToSource < vertex.getDistanceToSource()) {
                    vertex.setDistanceToSource(alternativeDistanceToSource);
                }
            }
        }
        return this.visited;
    }

    public void initialize() {
        Map<String, Vertex> vertices = this.graph.getVertices();
        for (Vertex vertex : vertices.values()) {
            if (vertex.getName().equals(source.getName())) {
                vertex.setDistanceToSource(0);
            } else {
                vertex.setDistanceToSource(POSITIVE_INFINITY);
            }
            this.unvisited.add(vertex);
        }
    }

    public Vertex findVertexWithMinDistanceToSource() {
        Vertex minVertex = this.unvisited.get(0);
        for (Vertex vertex : this.unvisited) {
            if (vertex.getDistanceToSource() <= minVertex.getDistanceToSource()) {
                minVertex = vertex;
            }
        }
        return minVertex;
    }

    public Map<Vertex, Double> findDistancesToUnvisitedNeighbors(Vertex vertex) {
        Map<Vertex, Double> neighbors = new HashMap<>();
        List<String> visitedListNames = visited.stream().map(v -> v.getName()).toList();

        for (Edge edge : this.graph.getEdges()) {
            if (edge.getStartVertex().equals(vertex.getName())) {
                Vertex endVertex = graph.getVertices().get(edge.getEndVertex());
                    if (!visitedListNames.contains(endVertex.getName())) {
                        neighbors.put(endVertex, edge.getDistance());
                    }
            }
        }
        return neighbors;
    }


}
