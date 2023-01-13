package com.example.backend.service;

import com.example.backend.client.OpenServiceApiClient;
import com.example.backend.exception.TripNotRegisteredException;
import com.example.backend.model.Location;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import com.example.backend.pathfinding.FindShortestPath;
import com.example.backend.pathfinding.Vertex;
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
        Trip trip = new Trip("xyz1", "My Trip", new ArrayList<>(List.of(
                new Location("abc1", "Kölner Dom", 50.941386546092225, 6.958270670147375),
                new Location("abc2", "Planten un Blomen", 53.5625456617408, 9.98188182570993)
        )));

        TripRepo tripRepo = mock(TripRepo.class);
        OpenServiceApiClient client = mock(OpenServiceApiClient.class);
        FindShortestPath path = mock(FindShortestPath.class);

        when(tripRepo.findById("xyz1")).thenReturn(Optional.of(trip));
        when(client.getMatrixResponse(trip)).thenReturn
                (new MatrixServiceResponse(
                        List.of(
                                List.of(50., 100.),
                                List.of(100., 50.)
                        )));
        when(path.findShortestPath(any(), any())).thenReturn(List.of(
                new Vertex("abc2", 0),
                new Vertex("abc1", 0)));

        LocationService locationService = new LocationService(tripRepo, client, path);
        List<Location> actual = locationService.getShortestPathById("xyz1");

        Assertions.assertEquals(List.of(
                new Location("abc2", "Planten un Blomen", 53.5625456617408, 9.98188182570993),
                new Location("abc1", "Kölner Dom", 50.941386546092225, 6.958270670147375)
        ), actual);
        verify(tripRepo).findById("xyz1");
        verify(client).getMatrixResponse(trip);
        verify(path).findShortestPath(any(),any());

    }

    @Test
    void getShortestPathById_Throws404WhenIdNotRegistered() {

        TripRepo tripRepo = mock(TripRepo.class);
        OpenServiceApiClient client = mock(OpenServiceApiClient.class);
        FindShortestPath path = mock(FindShortestPath.class);

        when(tripRepo.findById("not-registered")).thenReturn(Optional.ofNullable(null));

        LocationService locationService = new LocationService(tripRepo, client, path);

        Assertions.assertThrows(TripNotRegisteredException.class, () -> locationService.getShortestPathById("not-registered"));
    }
}