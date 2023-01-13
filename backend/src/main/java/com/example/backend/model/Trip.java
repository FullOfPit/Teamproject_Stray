package com.example.backend.model;

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

    public List<String> getLocationNames(){
        List<String> locationNames = new ArrayList<>();

        for (Location location : locations) {
            locationNames.add(location.getName());
        }
        return locationNames;
    }

    public List<List<Double>> getLocationGeos(){
        List<List<Double>> locationGeos = new ArrayList<>();

        for (Location location : locations) {
            locationGeos.add(
                    List.of(
                            location.getLongitude(),
                            location.getLatitude()
                    )
            );
        }
        return locationGeos;
    }
}
