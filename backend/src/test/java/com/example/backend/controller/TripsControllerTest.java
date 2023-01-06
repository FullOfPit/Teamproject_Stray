package com.example.backend.controller;

import com.example.backend.model.Location;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void getAll_returnEmptyWhenNoData() throws Exception {
        //when and then
        mvc.perform(get("/api/trips"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]",true));
    }

    @Test
    void getAll_returnAllTripsWhenTripsExist() throws Exception {
        //given
        Location location1 =new Location(1,"Kölner Dom",50.941386546092225,6.958270670147375);
        Location location2 =new Location(2,"Planten un Blomen",53.5625456617408,9.98188182570993);

        List<Trip> trips =new ArrayList<>(List.of(
                new Trip("abc1","My Trip",  List.of(location1,location2)),
                new Trip("abc2","My Trip 2", new ArrayList<>())
        ));

        this.tripRepo.saveAll(trips);

        String expected = """
                [{
                    "id":"abc1",
                    "title": "My Trip",
                    "locations": [{
                       "id": 1,
                       "name": "Kölner Dom",
                       "latitude": 50.941386546092225,
                       "longitude": 6.958270670147375
                     },{
                        "id": 2,
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
                .andExpect(content().json(expected,true));
    }
}