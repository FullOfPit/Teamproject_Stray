package com.example.backend.service;

import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TripServiceTest {

    @Test
    void getAll_returnTripsFromRepo() {
        //given
        List<Trip> expected =new ArrayList<>(List.of(
                new Trip("abc1","My Trip", new ArrayList<>()),
                new Trip("abc2","My Trip 2", new ArrayList<>())
        ));
        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.findAll()).thenReturn(expected);

        //when
        TripService tripService = new TripService(tripRepo);
        List<Trip> actual = tripService.getAll();

        //then
        assertEquals(expected, actual);
        verify(tripRepo).findAll();
    }
}