package com.example.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TripNotRegisteredException extends Exception{
    public TripNotRegisteredException() {
        super("Trip not registered");
    }
}
