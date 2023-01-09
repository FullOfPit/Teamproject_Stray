package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        while (!unvisited.isEmpty()) {
            Vertex evaluatedVertex = findVertexWithMinDistanceToSource();
            unvisited.remove(evaluatedVertex);
            visited.add(evaluatedVertex);

            Map<Vertex, Double> neighbors = findNeighborVerticesInUnvisited(evaluatedVertex);
            for (Vertex vertex : neighbors.keySet()) {
                double distance = neighbors.get(vertex);
                double alternativeDistanceToSource = evaluatedVertex.getDistanceToSource() + distance;
                if (alternativeDistanceToSource < vertex.getDistanceToSource()) {
                    vertex.setDistanceToSource(alternativeDistanceToSource);
                    vertex.setPreviousVertex(vertex.getName());
                }
            }
        }
        return visited;
    }

    public void initialize() {
        Map<String, Vertex> vertices = this.graph.getVertices();
        for (Vertex vertex : vertices.values()) {
            if (vertex.getName().equals(source.getName())) {
                vertex.setDistanceToSource(0);
            } else {
                vertex.setDistanceToSource(POSITIVE_INFINITY);
            }
            vertex.setPreviousVertex("Null");
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

    public Map<Vertex, Double> findNeighborVerticesInUnvisited(Vertex vertex) {
        Map<Vertex, Double> neighbors = new HashMap<>();
        for (Edge edge : this.graph.getEdges()) {
            if (edge.getStartVertex().equals(vertex.getName())) {
                Vertex endVertex = graph.getVertices().get(edge.getEndVertex());
                neighbors.put(endVertex, edge.getDistance());
            }
        }
        return neighbors;
    }


}
