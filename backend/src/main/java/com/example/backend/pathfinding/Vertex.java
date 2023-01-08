package com.example.backend.pathfinding;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Vertex {

    private String data;
    private List<Edge> edges;

    public Vertex(String data) {
        this.data = data;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Vertex vertex, double weight) {
        if (vertex.getClass() == Vertex.class) {
            this.edges.add(new Edge(this, vertex, weight));
        } else {
            throw new Error("Edge start and end must be vertices");
        }
    }

    public void removeEdge(Vertex vertex) {
        this.edges = this.edges.stream().filter(
                        (edge) -> (!edge.getEnd().equals(vertex))
                )
                .collect(Collectors.toList());
    }
}
