package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vertex {
    private String name;
    private double distanceToSource;
}
