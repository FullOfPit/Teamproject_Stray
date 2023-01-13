package com.example.backend.service;

import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.generator.IdGenerator;
import com.example.backend.generator.TimeStampGenerator;
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

    private final TimeStampGenerator timeStampGenerator;

    public List<Trip> getAll() {
        return this.tripRepo.findAll();
    }

    public Trip getById(String id) throws Exception {
        //Refactor redundancy
        return this.tripRepo.findById(id).orElseThrow(TripNotRegisteredException::new);
    }

    public Trip add(Trip trip) {
        for (Location location : trip.getLocations()) {
            if (location.getId() == null) {
                location.setId(this.idGenerator.generateRandomId());
            }
        }
        trip.setTripTimeStamp(timeStampGenerator.generateTimeStamp());

        return this.tripRepo.save(trip);
    }

    public void deleteById(String id) throws TripNotRegisteredException {
        if (this.tripRepo.existsById(id)) {
            this.tripRepo.deleteById(id);
        } else {
            throw new TripNotRegisteredException();
        }
    }

    public Trip update(String id, Trip trip) throws TripNotRegisteredException {
        trip.setId(id);

        if (!this.tripRepo.existsById(id)) {
            throw new TripNotRegisteredException();
        }

        return this.add(trip);
    }
}
