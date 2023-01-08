package com.example.backend.service;

import com.example.backend.model.Location;
import com.example.backend.model.MatrixServiceRequest;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripService {
    private final TripRepo tripRepo;

    public List<Trip> getAll() {
        return this.tripRepo.findAll();
    }

    public Trip add(Trip trip) {
        List<Location> locations = trip.getLocations();
        for (int i = 0; i < locations.size(); i++) {
            Location location = locations.get(i);
            location.setId(i + 1);
        }

        return this.tripRepo.save(trip);
    }

    public List<Location> getShortestPathById(String id) {
        // @ToDo bestimmte Teile der Logik in einzelne Klassen auslagern, damit die ganze Logik nicht im Service hängt (Stichwort: Single-Responsibility)

        // locations von trip mit id aus der DB holen
        Optional<Trip> optionalTrip = this.tripRepo.findById(id);

        if (optionalTrip.isEmpty()) {
            return new ArrayList<>();
        }

        Trip trip = optionalTrip.get();


        // locations in format umwandeln, was die API benötigt
        List<List<Double>> locations = new ArrayList<>();

        for (Location location : trip.getLocations()) {
            locations.add(
                    List.of(
                            location.getLongitude(),
                            location.getLatitude()
                    )
            );
        }

        MatrixServiceRequest matrixServiceRequest = new MatrixServiceRequest(locations);

        // API aufrufen und distancen abrufen
        /**
         * POST https://api.openrouteservice.org/v2/matrix/foot-walking
         * {
         *      "locations": [
         *          [9.70093,48.477473], // longitude,latitude
         *          [9.207916,49.153868],
         *          [37.573242,55.801281],
         *          [115.663757,38.106467]
         *      ],
         *      "metrics": ["distance"]
         * }
         *
         *
         * Response from API
         * {
         *   "distances": [
         *     [
         *       0,
         *       140.85,
         *       2421.65,
         *       9871.24
         *     ],
         *     [
         *       139.76,
         *       0,
         *       2373.12,
         *       9822.71
         *     ],
         *     [
         *       2369.48,
         *       2321.55,
         *       0,
         *       7501.35
         *     ],
         *     [
         *       9801.71,
         *       9753.77,
         *       7448.64,
         *       0
         *     ]
         *   ]
         *   // ...
         * }
         */
        // @ToDo Model erstellen, um API Request abzubilden
        // @ToDO Model erstellen, um API Response abzubilden
            WebClient client = WebClient.create("https://api.openrouteservice.org/v2/matrix");
            MatrixServiceResponse matrixServiceResponse = client
                .post()
                .uri("/foot-walking")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "5b3ce3597851110001cf6248c1d090744eaa41c1b4755e597d609c57")
                .bodyValue(matrixServiceRequest) // Object der ApiRequest Klasse mit Daten, die wir schicken wollen
                .retrieve()
                .toEntity(MatrixServiceResponse.class) // ApiResponse Klasse
                .block()
                .getBody();


        // shortest path berechnen -> damit muss liste von locations sortiert werden

        // return sortierte liste mit locations

        return new ArrayList<>();

    }
}
