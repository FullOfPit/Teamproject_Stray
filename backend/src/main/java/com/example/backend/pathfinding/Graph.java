package com.example.backend.pathfinding;

import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    private List<Vertex> vertices;
    private boolean isDirected;
    private boolean isWeighted;

    public Graph(boolean isDirected, boolean isWeighted) {
        this.isDirected = isDirected;
        this.isWeighted = isWeighted;
    }

    public Vertex addVertex(String data) {
        Vertex newVertex = new Vertex(data);
        this.vertices.add(newVertex);
        return newVertex;
    }

    public void removeVertex(Vertex vertexToRemove) {
        this.vertices = this.vertices.stream().filter(
                        (vertex) -> !vertex.equals(vertexToRemove))
                .collect(Collectors.toList());
    }

    public void addEdge(Vertex startVertex, Vertex endVertex, double weight) {
        double edgeWeight = this.isWeighted ? weight : 0;

        if (startVertex.getClass() == Vertex.class && endVertex.getClass() == Vertex.class) {
            startVertex.addEdge(endVertex, edgeWeight);
            if (!this.isDirected) {
                endVertex.addEdge(startVertex, edgeWeight);
            }
        } else {
            throw new Error("Expected Vertex arguments");
        }
    }

    public void removeEdge(Vertex startVertex, Vertex endVertex) {
        if (startVertex.getClass() == Vertex.class && endVertex.getClass() == Vertex.class) {
            startVertex.removeEdge(endVertex);
            if (!this.isDirected) {
                endVertex.removeEdge(startVertex);
            }
        } else {
            throw new Error("Expected Vertex arguments");
        }
    }

    public Vertex getVertexByData(String data) {
        return this.vertices.stream().filter(
                    (vertex -> vertex.getData().equals(data))
                )
                .findFirst().orElse(null);
    }
}
