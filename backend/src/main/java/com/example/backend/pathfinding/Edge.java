package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Edge {
    private String startVertex;
    private String endVertex;
    private double distance;
}
