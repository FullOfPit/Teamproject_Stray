package com.example.backend.service;

import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepo tripRepo;

    public List<Trip> getAll() {
        return this.tripRepo.findAll();
    }

    public Trip add(Trip trip) {
        return this.tripRepo.save(trip);
    }
}
