package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    private String id;

    private String title;
    private List<Location> locations = new ArrayList<>();

    @JsonIgnore
    public List<String> getLocationIds(){
        return this.getLocations().stream().map(location -> location.getId()).toList();
    }

    @JsonIgnore
    public List<List<Double>> getLocationGeos(){
        return this.getLocations()
                .stream()
                .map(location ->
                    List.of(
                        location.getLongitude(),
                        location.getLatitude()))
                .toList();
    }
}
