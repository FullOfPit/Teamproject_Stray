package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Graph {
    private Map<String,Vertex> vertices;
    private List<Edge> edges;
    public void addVertex(String vertexName){
        vertices.put(vertexName,new Vertex(vertexName,0));
    }

    public void addDirectedEdge(Edge edge){
        edges.add(edge);
    }
}
