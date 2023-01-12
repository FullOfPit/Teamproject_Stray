package com.example.backend.controller;

import com.example.backend.model.Location;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TripsControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private TripRepo tripRepo;

    private Location testLocation1() {
        return new Location("xyz1", "Kölner Dom", 50.941386546092225, 6.958270670147375);
    }
    private Location testLocation2() {
        return new Location("xyz2", "Planten un Blomen", 53.5625456617408, 9.98188182570993);
    }
    private Trip testTrip1() {
        return new Trip("abc1", "My Trip", List.of(testLocation1(), testLocation2()));
    }
    private Trip testTrip2() {
        return new Trip("abc2", "My Trip 2", new ArrayList<>());
    }

    private List<Trip> testTripList() {
        return List.of(
                testTrip1(),
                testTrip2()
        );
    }

    @Test
    void getAll_returnEmptyWhenNoData() throws Exception {
        //when and then
        mvc.perform(get("/api/trips"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", true));
    }

    @Test
    void getAll_returnAllTripsWhenTripsExist() throws Exception {
        //given
        this.tripRepo.saveAll(testTripList());

        String expected = """
                [{
                    "id":"abc1",
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
                },{
                    "id":"abc2",
                    "title": "My Trip 2",
                    "locations":[]
                }]
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
                .andExpect(jsonPath("$.locations.*.id", notNullValue()));

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
}