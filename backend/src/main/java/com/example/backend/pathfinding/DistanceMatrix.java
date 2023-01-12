package com.example.backend.pathfinding;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
public class DistanceMatrix {
    List<String> vertexNames;
    List<List<Double>> distanceMatrix;

    public Graph toGraph(){
        Graph graph = new Graph(new HashMap<>(), new ArrayList<>());

        vertexNames.forEach(name -> graph.addVertex(name));

        for (int i =0; i< distanceMatrix.size(); i++){
            for (int j =0; j< distanceMatrix.get(0).size(); j++){
                if (distanceMatrix.get(i).get(j)!=0) {
                    graph.addDirectedEdge(
                            new Edge(vertexNames.get(i),
                                    vertexNames.get(j),
                                    distanceMatrix.get(i).get(j)
                            )
                    );
                }
            }
        }

        return graph;
    }
}
