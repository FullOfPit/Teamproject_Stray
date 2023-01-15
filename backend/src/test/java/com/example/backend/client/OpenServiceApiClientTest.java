package com.example.backend.client;

import com.example.backend.model.MatrixServiceResponse;
import com.example.backend.model.Trip;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OpenServiceApiClientTest {

    private MockWebServer mockWebServer;

    @BeforeEach
    void setupMockWebServer() throws IOException {
        this.mockWebServer = new MockWebServer();
        this.mockWebServer.start();
    }

    @AfterEach
    void shutdownMockWebServer() throws IOException {
        this.mockWebServer.shutdown();
    }

    @Test
    void getMatrixResponse_returnsMatrixServiceResponse() throws InterruptedException {
        // given
        String expectedApiKey = "some-key";

        MockResponse mockResponse = new MockResponse()
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .setResponseCode(HttpStatus.OK.value())
                .setBody("""
                            {
                                "durations": [
                                    [
                                        0,
                                        23.4
                                    ],
                                    [
                                        23.4,
                                        0
                                    ]
                                ]
                            }
                        """);

        this.mockWebServer.enqueue(mockResponse);

        MatrixServiceResponse expectedResponse = new MatrixServiceResponse();
        expectedResponse.setDurations(List.of(
                List.of(0.0, 23.4),
                List.of(23.4, 0.0)
        ));

        OpenServiceApiClient sut = new OpenServiceApiClient(
                WebClient.builder(),
                this.mockWebServer.url("/").toString(),
                expectedApiKey
        );

        Trip trip = new Trip();

        // when
        MatrixServiceResponse actualResponse = sut.getMatrixResponse(trip);

        // then
        assertEquals(expectedResponse, actualResponse);

        RecordedRequest recordedRequest = this.mockWebServer.takeRequest();

        assertEquals(HttpMethod.POST.name(), recordedRequest.getMethod());
        assertEquals("/foot-walking", recordedRequest.getPath());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, recordedRequest.getHeader(HttpHeaders.CONTENT_TYPE));
        assertEquals(expectedApiKey, recordedRequest.getHeader(HttpHeaders.AUTHORIZATION));
    }



}