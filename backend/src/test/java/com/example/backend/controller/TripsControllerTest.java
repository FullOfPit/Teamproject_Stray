package com.example.backend.controller;

import com.example.backend.client.OpenServiceApiClient;
import com.example.backend.model.Location;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TripsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OpenServiceApiClient client;

    @Autowired
    private TripRepo tripRepo;

    private Location testLocation1() {
        return new Location("xyz1", "Kölner Dom", 50.941386546092225, 6.958270670147375);
    }
    private Location testLocation2() {
        return new Location("xyz2", "Planten un Blomen", 53.5625456617408, 9.98188182570993);
    }
    private Trip testTrip1() {
        return new Trip("abc1", testTimeStamp(), "My Trip", List.of(testLocation1(), testLocation2()));
    }
    private Trip testTrip2() {
        return new Trip("abc2", testTimeStamp(), "My Trip 2", new ArrayList<>());
    }

    private LocalDateTime testTimeStamp() {
        return LocalDateTime.of(2020, 1, 1, 0,0);
    }

    @Test
    void getAll_returnEmptyWhenNoData() throws Exception {
        //when and then
        mvc.perform(get("/api/trips"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }

    @Test
    void getAll_returnAllTripsSortedByTimestampDescWhenTripsExist() throws Exception {
        //given
        LocalDateTime dateTime = LocalDateTime.of(2020, 1, 1, 0, 0, 0);

        Trip trip1 = testTrip1();
        trip1.setTripTimeStamp(dateTime);

        Trip trip2 = testTrip2();
        trip2.setTripTimeStamp(dateTime.plusSeconds(1));

        this.tripRepo.saveAll(List.of(trip1, trip2));

        String expected = """
                [
                    {
                        "id":"abc2",
                        "tripTimeStamp" : "2020-01-01T00:00:01",
                        "title": "My Trip 2",
                        "locations":[]
                    },
                    {
                        "id":"abc1",
                        "tripTimeStamp" : "2020-01-01T00:00:00",
                        "title": "My Trip",
                        "locations": [
                            {
                               "id": "xyz1",
                               "name": "Kölner Dom",
                               "latitude": 50.941386546092225,
                               "longitude": 6.958270670147375
                            },
                            {
                                "id": "xyz2",
                                "name": "Planten un Blomen",
                                "latitude": 53.5625456617408,
                                "longitude": 9.98188182570993
                            }
                        ]
                    }
                ]
                """;

        //when and then
        mvc.perform(get("/api/trips"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected, true));
    }

    @Test
    void getById_returnCorrectTripWhenRequested() throws Exception {
        //Given
        this.tripRepo.save(testTrip1());

        String expected = """
                    {
                        "id":"abc1",
                        "tripTimeStamp" : "2020-01-01T00:00:00",
                        "title": "My Trip",
                        "locations": [{
                           "id": "xyz1",
                           "name": "Kölner Dom",
                           "latitude": 50.941386546092225,
                           "longitude": 6.958270670147375
                         },{
                            "id": "xyz2",
                            "name": "Planten un Blomen",
                            "latitude": 53.5625456617408,
                            "longitude": 9.98188182570993
                        }]
                    }
                """;

        //When - Then
        mvc.perform(get("/api/trips/abc1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected, true));
    }

    @Test
    void getById_return404ErrorWhenIdNotRegistered() throws Exception {
        mvc.perform(get("/api/trips/abc1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void add_returnTripWhenAddTrip() throws Exception {
        String given = """
                {
                    "title": "My Trip",
                    "locations": [{
                       "name": "Kölner Dom",
                       "latitude": 50.941386546092225,
                       "longitude": 6.958270670147375
                     },{
                        "name": "Planten un Blomen",
                        "latitude": 53.5625456617408,
                        "longitude": 9.98188182570993
                    }]
                }
                """;

        // when + then
        this.mvc.perform(
                        post("/api/trips")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(given))
                .andExpect(status().isOk())
                .andExpect(content().json(given))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.tripTimeStamp", notNullValue()))
                .andExpect(jsonPath("$.locations.*.id", allOf(
                        hasSize(2),
                        everyItem(notNullValue())
                )));
    }

    @Test
    void deleteById_DeletesTripFromListCorrectly() throws Exception {
        Trip testTrip = testTrip1();
        this.tripRepo.save(testTrip);

        mvc.perform(delete("/api/trips/" + testTrip.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void deleteById_Throws404WhenIdNotRegistered() throws Exception {
        mvc.perform(delete("/api/trips/NONEREGISTEREDID"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getShortestPath_Throws404WhenIdNotRegistered() throws Exception {
        mvc.perform(get("/api/trips/NONEREGISTEREDID/shortest-path"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getShortestPath_returnSortedLocationsWhenFindShortestPathById() throws Exception {
        Trip trip = new Trip("abc1", "My Trip", new ArrayList<>(List.of(
                new Location("xyz1", "Kölner Dom", 50.941386546092225, 6.958270670147375),
                new Location("xyz2", "Planten un Blomen", 53.5625456617408, 9.98188182570993)
        )));
        this.tripRepo.save(trip);

        when(client.getMatrixResponse(trip)).thenReturn(new MatrixServiceResponse(
                List.of(
                        List.of(50.0, 100.0),
                        List.of(100.0, 50.0)
                )));

        String expected = """
                        [{
                           "id": "xyz1",
                           "name": "Kölner Dom",
                           "latitude": 50.941386546092225,
                           "longitude": 6.958270670147375
                         },{
                            "id": "xyz2",
                            "name": "Planten un Blomen",
                            "latitude": 53.5625456617408,
                            "longitude": 9.98188182570993
                        }]
                """;
        mvc.perform(get("/api/trips/abc1/shortest-path"))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void update_updatesTripIfExists() throws Exception {
        // given
        Trip existingTrip = testTrip1();
        this.tripRepo.save(existingTrip);

        String expectedJsonNonStrict = String.format("""
                 {
                    "id": "%s",
                    "title": "Changed title",
                    "locations": [
                        {
                            "id": "xyz2",
                            "name": "Updated location",
                            "latitude": 53.5625456617408,
                            "longitude": 9.98188182570993
                        },
                        {
                            "name": "New Location",
                            "latitude": 23.34,
                            "longitude": 12.43
                        }
                    ]
                }
                """, existingTrip.getId());

        // when + then
        this.mvc.perform(
                        put("/api/trips/" + existingTrip.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(expectedJsonNonStrict))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonNonStrict))
                .andExpect(jsonPath("$.locations.*.id", allOf(
                        hasSize(2),
                        everyItem(notNullValue())
                )));
    }

    @Test
    void update_return404ErrorWhenIdNotRegistered() throws Exception {
        // given
        String givenJson = """
                {
                    "id": "does-not-exist",
                    "title": "Does not matter",
                    "locations": []
                }
                """;

        // when + then
        mvc.perform(put("/api/trips/does-not-exist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenJson))
                .andExpect(status().isNotFound());
    }

}