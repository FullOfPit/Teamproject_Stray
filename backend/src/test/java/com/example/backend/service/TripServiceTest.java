package com.example.backend.service;

import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.generator.IdGenerator;
import com.example.backend.generator.TimeStampGenerator;
import com.example.backend.model.Location;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class TripServiceTest {


    private LocalDateTime testTimeStamp() {
        return LocalDateTime.of(2020, 1, 1, 0, 0);
    }

    @Test
    void getAll_returnTripsFromRepo() {
        //given
        List<Trip> expected = new ArrayList<>(List.of(
                new Trip("abc1",testTimeStamp(), "My Trip", new ArrayList<>()),
                new Trip("abc2",testTimeStamp(), "My Trip 2", new ArrayList<>())
        ));
        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.findAll()).thenReturn(expected);

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        //when
        TripService tripService = new TripService(tripRepo, idGenerator,timeStampGenerator);
        List<Trip> actual = tripService.getAll();

        //then
        assertEquals(expected, actual);
        verify(tripRepo).findAll();
    }

    @Test
    void add_addsTripToRepoAndReturnsTrip() {
        // given
        Trip trip = new Trip("abc123", "My Trip", new ArrayList<>());

        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.save(trip)).thenReturn(trip);

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        // when
        TripService tripService = new TripService(tripRepo, idGenerator, timeStampGenerator);
        Trip actual = tripService.add(trip);

        // then
        assertEquals(trip, actual);

        verify(tripRepo).save(trip);
    }

    @Test
    void add_generatesIdsForAllLocationsWithoutId() {
        // given
        Trip trip = new Trip("abc1", "My Trip", List.of(
                new Location("Kölner Dom", 50.941386546092225, 6.958270670147375),
                new Location("existing-location-id", "Planten un Blomen", 53.5625456617408, 9.98188182570993)
        ));

        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.save(any())).then(returnsFirstArg());

        IdGenerator idGenerator = mock(IdGenerator.class);
        when(idGenerator.generateRandomId()).thenReturn("new-location-id");

        List<String> expectedLocationIds = List.of("new-location-id", "existing-location-id");

        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);
        when(timeStampGenerator.generateTimeStamp())
                .thenReturn(LocalDateTime.of(2020, 1, 1, 0, 0));

        // when
        TripService tripService = new TripService(tripRepo, idGenerator, timeStampGenerator);
        Trip actual = tripService.add(trip);

        // then
        List<String> actualLocationIds = actual.getLocations().stream().map(Location::getId).toList();
        assertEquals(expectedLocationIds, actualLocationIds);

        verify(idGenerator, times(1)).generateRandomId();
    }

    @Test
    void getById_ReturnsTripCorrectlyWhenRequested() throws Exception {
        //Given
        TripRepo tripRepo = mock(TripRepo.class);

        Trip testTrip = new Trip("TestId", testTimeStamp(), "TestTripTitle", new ArrayList<>());
        when(tripRepo.findById("TestId")).thenReturn(Optional.of(testTrip));

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        //When

        TripService tripService = new TripService(tripRepo, idGenerator, timeStampGenerator);
        Trip actual = tripService.getById("TestId");

        //Then
        Assertions.assertEquals(new Trip("TestId",
                LocalDateTime.of(2020, 1, 1, 0, 0),
                "TestTripTitle", new ArrayList<>()),
                actual);
        verify(tripRepo).findById("TestId");
    }

    @Test
    void getById_ReturnTripNotRegisteredException() {
        //Given
        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.findById("TestId")).thenReturn(Optional.ofNullable(null));

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        //When
        TripService tripService = new TripService(tripRepo, idGenerator, timeStampGenerator);
        //Then
        Assertions.assertThrows(TripNotRegisteredException.class, () -> tripService.getById("TestId"));
    }

    @Test
    void deleteById_DeletesTripIfRegistered() throws TripNotRegisteredException {
        //Given
        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.existsById("abc1")).thenReturn(true);

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        TripService tripService = new TripService(tripRepo, idGenerator, timeStampGenerator);
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

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        TripService tripService = new TripService(tripRepo, idGenerator, timeStampGenerator);
        //When - Then
        Assertions.assertThrows(TripNotRegisteredException.class, () -> tripService.deleteById("abc1"));
    }

    @Test
    void update_updatesTrip_whenExists() throws TripNotRegisteredException {
        // given
        Trip trip = new Trip("some-id", "some trip", new ArrayList<>());

        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.existsById(any())).thenReturn(true);
        when(tripRepo.save(any())).then(returnsFirstArg());

        IdGenerator idGenerator = mock(IdGenerator.class);
        when(idGenerator.generateRandomId()).thenReturn("new-location-id");

        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        // when
        TripService sut = new TripService(tripRepo, idGenerator, timeStampGenerator);
        Trip actual = sut.update(trip.getId(), trip);

        // then
        assertEquals(actual, trip);

        verify(tripRepo).save(any());
    }

    @Test
    void update_ensuresThatIdForNewLocationsIsGenerated() throws TripNotRegisteredException {
        // given
        Trip trip = new Trip("some-id", "some trip", new ArrayList<>(List.of(
                new Location("Kölner Dom", 50.941386546092225, 6.958270670147375),
                new Location("existing-location-id", "Planten un Blomen", 53.5625456617408, 9.98188182570993)
        )));

        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.existsById(any())).thenReturn(true);
        when(tripRepo.save(any())).then(returnsFirstArg());

        IdGenerator idGenerator = mock(IdGenerator.class);
        when(idGenerator.generateRandomId()).thenReturn("new-location-id");

        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        List<String> expectedIds = List.of("new-location-id", "existing-location-id");

        // when
        TripService sut = new TripService(tripRepo, idGenerator, timeStampGenerator);
        Trip actual = sut.update(trip.getId(), trip);

        // then
        List<String> actualIds = actual.getLocations().stream().map(Location::getId).toList();
        assertEquals(expectedIds, actualIds);

        verify(idGenerator, times(1)).generateRandomId();
        verify(tripRepo).save(any());
    }

    @Test
    void update_ensuresMismatchBetweenGivenIdAndIdOfTripIsFixed() throws TripNotRegisteredException {
        // given
        String expectedId = "given-id";
        Trip trip = new Trip("id-to-be-overwritten", "some trip", new ArrayList<>());

        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.existsById(expectedId)).thenReturn(true);
        when(tripRepo.save(any())).then(returnsFirstArg());

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        // when
        TripService sut = new TripService(tripRepo, idGenerator, timeStampGenerator);
        Trip actual = sut.update(expectedId, trip);

        // then
        assertEquals(expectedId, actual.getId());

        verify(tripRepo).save(any());
    }

    @Test
    void update_throwsTripNotRegisteredException_whenNotExists() {
        // given
        TripRepo tripRepo = mock(TripRepo.class);
        when(tripRepo.existsById(any())).thenReturn(false);

        IdGenerator idGenerator = mock(IdGenerator.class);
        TimeStampGenerator timeStampGenerator = mock(TimeStampGenerator.class);

        // when + then
        TripService sut = new TripService(tripRepo, idGenerator, timeStampGenerator);

        assertThrows(TripNotRegisteredException.class, () -> sut.update("does-not-matter", new Trip()));
    }

}