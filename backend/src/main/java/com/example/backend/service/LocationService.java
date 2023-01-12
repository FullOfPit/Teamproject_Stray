package com.example.backend.service;

import com.example.backend.model.Location;
import com.example.backend.model.MatrixServiceRequest;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import com.example.backend.repository.TripRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    @Value("${open_route_service.api_key}")
    private String apiKey;

    @Value("${open_route_service.url}")
    private String apiUrl;
    private final TripRepo tripRepo;

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
        WebClient client = WebClient.create(apiUrl + "/matrix");
        MatrixServiceResponse matrixServiceResponse = client
                .post()
                .uri("/foot-walking")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, apiKey)
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
