package com.example.backend.service;

import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.generator.IdGenerator;
import com.example.backend.model.Location;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepo tripRepo;

    private final IdGenerator idGenerator;

    public List<Trip> getAll() {
        return this.tripRepo.findAll();
    }

    public Trip getById(String id) throws Exception {
        //Refactor redundancy
        return this.tripRepo.findById(id).orElseThrow(TripNotRegisteredException::new);
    }

    public Trip add(Trip trip) {
        for (Location location : trip.getLocations()) {
            location.setId(this.idGenerator.generateRandomId());
        }

        return this.tripRepo.save(trip);
    }

    public void deleteById(String id) throws TripNotRegisteredException {
        if (this.tripRepo.existsById(id)) {
            this.tripRepo.deleteById(id);
        } else {
            throw new TripNotRegisteredException();
        }
    }
}
