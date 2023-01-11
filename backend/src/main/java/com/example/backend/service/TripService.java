package com.example.backend.service;

import com.example.backend.exception.TripNotRegisteredException;
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

    public List<Trip> getAll() {
        return this.tripRepo.findAll();
    }

    public Trip getById(String Id) throws Exception {
        //Refactor redundancy
        return this.tripRepo.findById(Id).orElseThrow(TripNotRegisteredException::new);
    }

    public Trip add(Trip trip) {
        List<Location> locations = trip.getLocations();
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            location.setId(i + 1);
        }

        return this.tripRepo.save(trip);
    }

    public void deleteById(String Id) throws TripNotRegisteredException {
        if (this.tripRepo.existsById(Id)) {
            this.tripRepo.deleteById(Id);
        } else {
            throw new TripNotRegisteredException();
        }

    }
}
