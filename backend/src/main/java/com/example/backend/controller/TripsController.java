package com.example.backend.controller;

import com.example.backend.exception.TripNotRegisteredException;
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

    @GetMapping(path = "/{Id}")
    public Trip getById (@PathVariable String Id) throws Exception {
        return this.tripService.getById(Id);
    }

    @PostMapping
    public Trip add(@RequestBody Trip trip){
        return this.tripService.add(trip);
    }

    @DeleteMapping(path = "/{Id}")
    public void deleteById(@PathVariable String Id) throws TripNotRegisteredException {
        this.tripService.deleteById(Id);
    }

}
