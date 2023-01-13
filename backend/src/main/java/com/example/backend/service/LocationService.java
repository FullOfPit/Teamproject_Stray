package com.example.backend.service;

import com.example.backend.client.OpenServiceApiClient;
import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.model.Location;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import com.example.backend.pathfinding.Matrix;
import com.example.backend.pathfinding.FindShortestPath;
import com.example.backend.pathfinding.Vertex;
import com.example.backend.repository.TripRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final TripRepo tripRepo;
    private final OpenServiceApiClient client;
    private final FindShortestPath path;

    public List<Location> getShortestPathById(String id) throws TripNotRegisteredException {

        Trip trip = this.tripRepo.findById(id).orElseThrow(TripNotRegisteredException::new);
        List<Location> locations = trip.getLocations();

        MatrixServiceResponse matrixServiceResponse = client.getMatrixResponse(trip);

        Matrix matrix = new Matrix(trip.getLocationIds(),matrixServiceResponse.getDurations());

        List<Vertex> shortestPath = this.path.findShortestPath(matrix.toGraph(),new Vertex(trip.getLocationIds().get(0), 0 ));

        List<String> order = shortestPath.stream().map(vertex -> vertex.getName()).toList();
        locations.sort(Comparator.comparing(location -> order.indexOf(location.getId())));

        return locations;

    }

}
