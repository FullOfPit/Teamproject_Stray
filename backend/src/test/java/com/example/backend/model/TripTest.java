package com.example.backend.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripTest {

    @Test
    void getLocationIds_returnsListOfIds() {
        // given
        List<Location> locations = List.of(
                new Location("location-1", "does not matter", 22.22, 8.88),
                new Location("location-2", "does not matter", 33.33, 9.99)
        );

        List<String> expected = List.of("location-1", "location-2");

        // when
        Trip sut = new Trip();
        sut.setLocations(locations);

        List<String> actual = sut.getLocationIds();

        // then
        assertEquals(expected, actual);
    }

    @Test
    void getLocationGeos_returnsListOfGeoData() {
        // given
        List<Location> locations = List.of(
                new Location("does not matter", 22.22, 8.88),
                new Location("does not matter", 33.33, 9.99)
        );

        List<List<Double>> expected = List.of(
                List.of(8.88, 22.22),
                List.of(9.99, 33.33)
        );

        // when
        Trip sut = new Trip();
        sut.setLocations(locations);

        List<List<Double>> actual = sut.getLocationGeos();

        // then
        assertEquals(expected, actual);
    }
}
