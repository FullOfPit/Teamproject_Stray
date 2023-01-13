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

    public List<String> getLocationIds(){
        List<String> locationIds = new ArrayList<>();

        for (Location location : locations) {
            locationIds.add(location.getId());
        }
        return locationIds;
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
