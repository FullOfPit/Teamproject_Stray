package com.example.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    private String id;
    private LocalDateTime tripTimeStamp;
    private String title;
    private List<Location> locations;
}
