package com.example.backend.client;

import com.example.backend.model.MatrixServiceRequest;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenServiceApiClient {
    @Value("${open_route_service.api_key}")
    private String apiKey;
    @Value("${open_route_service.url}")
    private String apiUrl;

    public MatrixServiceResponse getMatrixResponse(Trip trip ){
        WebClient client = WebClient.create(apiUrl + "/matrix");
        MatrixServiceRequest matrixServiceRequest = new MatrixServiceRequest(trip.getLocationGeos());

        return client
                .post()
                .uri("/foot-walking")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .bodyValue(matrixServiceRequest) // Object der ApiRequest Klasse mit Daten, die wir schicken wollen
                .retrieve()
                .toEntity(MatrixServiceResponse.class) // ApiResponse Klasse
                .block()
                .getBody();
    }

}
