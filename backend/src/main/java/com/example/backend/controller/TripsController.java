package com.example.backend.controller;

import com.example.backend.model.Trip;
import com.example.backend.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripsController {
    private final TripService tripService;

    @GetMapping
    public List<Trip> getAll() {
        return this.tripService.getAll();
    }

    @GetMapping(path = "/{id}")
    public Trip getById (@PathVariable String id) throws Exception {
        return this.tripService.getById(id);

    }

    @PostMapping
    public Trip add(@RequestBody Trip trip){
        return this.tripService.add(trip);
    }

}
