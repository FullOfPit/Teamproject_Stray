package com.example.backend.controller;

import com.example.backend.model.Location;
import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.model.Trip;
import com.example.backend.service.LocationService;
import com.example.backend.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripsController {
    private final TripService tripService;
    private final LocationService locationService;

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

    @DeleteMapping(path = "/{Id}")
    public void deleteById(@PathVariable String Id) throws TripNotRegisteredException {
        this.tripService.deleteById(Id);
    }

    @GetMapping("/{id}/shortest-path")
    public List<Location> getShortestPath(@PathVariable String id) throws TripNotRegisteredException {
        return this.locationService.getShortestPathById(id);
    }


}
