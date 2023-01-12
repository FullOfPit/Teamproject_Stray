package com.example.backend.service;

import com.example.backend.model.Location;
import com.example.backend.model.MatrixServiceRequest;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepo tripRepo;

    public List<Trip> getAll() {
        return this.tripRepo.findAll();
    }

    public Trip add(Trip trip) {
        List<Location> locations = trip.getLocations();
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            location.setId(i + 1);
        }

        return this.tripRepo.save(trip);
    }


}
