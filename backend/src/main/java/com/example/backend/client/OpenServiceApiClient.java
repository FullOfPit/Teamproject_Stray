package com.example.backend.client;

import com.example.backend.exception.ClientResponseException;
import com.example.backend.model.MatrixServiceRequest;
import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OpenServiceApiClient {
    private final WebClient client;

    public OpenServiceApiClient(
            WebClient.Builder clientBuilder,
            @Value("${open_route_service.url}") String apiUrl,
            @Value("${open_route_service.api_key}") String apiKey
    ) {
        this.client = clientBuilder
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, apiKey)
                .build();
    }

    public MatrixServiceResponse getMatrixResponse(Trip trip) {
        MatrixServiceRequest matrixServiceRequest = new MatrixServiceRequest(trip.getLocationGeos());

        return this.client
                .post()
                .uri("/foot-walking")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(matrixServiceRequest)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                    Mono.error(new ClientResponseException().getCause())
                )
                .toEntity(MatrixServiceResponse.class)
                .block()
                .getBody();
    }

}
