package com.example.backend.service;

import com.example.backend.client.OpenServiceApiClient;
import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.model.Location;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import com.example.backend.pathfinding.FindShortestPath;
import com.example.backend.repository.TripRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Test
    void getShortestPathById_WhenTripWith2LocationsThenReturnCorrectlyShortestPath() throws TripNotRegisteredException {
        Trip trip = new Trip("abc1", "My Trip", List.of(
                new Location("Kölner Dom", 50.941386546092225, 6.958270670147375),
                new Location("Planten un Blomen", 53.5625456617408, 9.98188182570993)
        ));

        TripRepo tripRepo = mock(TripRepo.class);
        OpenServiceApiClient client = mock(OpenServiceApiClient.class);

        when(tripRepo.findById("abc1")).thenReturn(Optional.of(trip));
        when(client.getMatrixResponse(trip)).thenReturn
                ( new MatrixServiceResponse(
                        List.of(
                            List.of(50.,100.),
                            List.of(100.,50.)
                )));

        LocationService locationService = new LocationService(tripRepo,client, new FindShortestPath(new ArrayList<>(), new ArrayList<>()));
        List<String> actual = locationService.getShortestPathById("abc1");

        Assertions.assertEquals(List.of("Kölner Dom","Planten un Blomen"),actual);
        verify(tripRepo).findById("abc1");
        verify(client).getMatrixResponse(trip);

    }

    @Test
    void getShortestPathById_Throws404WhenIdNotRegistered(){
        Trip trip = new Trip("not-registered", "", new ArrayList<>(new ArrayList<>()));

        TripRepo tripRepo = mock(TripRepo.class);
        OpenServiceApiClient client = mock(OpenServiceApiClient.class);

        when(tripRepo.findById("not-registered")).thenReturn(Optional.ofNullable(null));
        when(client.getMatrixResponse(trip)).thenReturn(new MatrixServiceResponse());

        LocationService locationService = new LocationService(tripRepo,client, new FindShortestPath(new ArrayList<>(), new ArrayList<>()));

        Assertions.assertThrows(TripNotRegisteredException.class, () -> locationService.getShortestPathById("not-registered"));
    }
}