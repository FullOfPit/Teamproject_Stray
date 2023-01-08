package com.example.backend.pathfinding;

import lombok.Data;

@Data
public class Edge {

    private Vertex start;
    private Vertex end;
    private double weight;

    public Edge(Vertex start, Vertex end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }


}
