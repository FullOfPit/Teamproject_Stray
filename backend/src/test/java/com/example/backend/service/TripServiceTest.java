package com.example.backend.service;

import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.model.Location;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TripServiceTest {

    @Test
    void getAll_returnTripsFromRepo() {
        //given
        List<Trip> expected = new ArrayList<>(List.of(
                new Trip("abc1", "My Trip", new ArrayList<>()),
                new Trip("abc2", "My Trip 2", new ArrayList<>())
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

    @Test
    void add_addsTripToRepoAndReturnsTrip() {
        // given
        Trip trip = new Trip("abc1", "My Trip", List.of(
                new Location("Kölner Dom", 50.941386546092225, 6.958270670147375),
                new Location("Planten un Blomen", 53.5625456617408, 9.98188182570993)
        ));

        Trip tripWithAddedLocationIds = new Trip("abc1", "My Trip", List.of(
                new Location(1, "Kölner Dom", 50.941386546092225, 6.958270670147375),
                new Location(2, "Planten un Blomen", 53.5625456617408, 9.98188182570993)
        ));

        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.save(tripWithAddedLocationIds)).thenReturn(tripWithAddedLocationIds);

        // when
        TripService tripService = new TripService(tripRepo);
        Trip actual = tripService.add(trip);

        // then
        assertEquals(tripWithAddedLocationIds, actual);
        verify(tripRepo).save(tripWithAddedLocationIds);
    }

    @Test
    void getById_ReturnsTripCorrectlyWhenRequested() throws Exception {
        //Given
        TripRepo tripRepo = mock(TripRepo.class);

        Trip testTrip = new Trip("TestId", "TestTripTitle", new ArrayList<>());
        when(tripRepo.findById("TestId")).thenReturn(Optional.of(testTrip));
        //When

        TripService tripService = new TripService(tripRepo);
        Trip actual = tripService.getById("TestId");

        //Then
        Assertions.assertEquals(new Trip("TestId", "TestTripTitle", new ArrayList<>()), actual);
        verify(tripRepo).findById("TestId");
    }

    @Test
    void getById_ReturnTripNotRegisteredException() {
        //Given
        TripRepo tripRepo = mock(TripRepo.class);
        given(tripRepo.findById("TestId")).willAnswer(invocationOnMock -> {throw new TripNotRegisteredException();});
        //When
        TripService tripService = new TripService(tripRepo);
        //Then
        Assertions.assertThrows(TripNotRegisteredException.class, () -> tripService.getById("TestId"));
    }

    @Test
    void deleteById_DeletesTripIfRegistered() throws TripNotRegisteredException {
        //Given
        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.existsById("abc1")).thenReturn(true);
        TripService tripService = new TripService(tripRepo);
        //When
        tripService.deleteById("abc1");
        //Then
        verify(tripRepo).deleteById("abc1");
    }

    @Test
    void deleteById_ThrowsTripNotRegisteredException_WhenNotRegistered() {
        //Given
        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.existsById("abc1")).thenReturn(false);
        TripService tripService = new TripService(tripRepo);
        //When - Then
        Assertions.assertThrows(TripNotRegisteredException.class, () -> tripService.deleteById("abc1"));
    }


}