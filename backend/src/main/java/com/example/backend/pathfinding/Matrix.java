package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class Matrix {
    List<String> vertexNames;
    List<List<Double>> matrix;

    public Graph toGraph(){
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());

        vertexNames.forEach(name -> graph.addVertex(name));

        for (int i =0; i< matrix.size(); i++){
            for (int j =0; j< matrix.get(0).size(); j++){
                if (matrix.get(i).get(j)!=0) {
                    graph.addDirectedEdge(
                            new Edge(vertexNames.get(i),
                                    vertexNames.get(j),
                                    matrix.get(i).get(j)
                            )
                    );
                }
            }
        }

        return graph;
    }
}
