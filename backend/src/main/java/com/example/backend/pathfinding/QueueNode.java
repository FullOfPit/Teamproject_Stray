package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueueNode {

    private Vertex vertex;
    private double priority;
}
