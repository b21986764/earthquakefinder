package com.example.earthquaketracker.service;

import com.example.earthquaketracker.model.Earthquake;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EarthquakeService {

    // URL for the earthquake API
    private static final String API_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=%s&endtime=%s";
    // Method to fetch earthquake data for a specific country for the past specified number of days
    public List<Earthquake> getEarthquakes(String country, int days) {
        String startDate = LocalDate.now().minusDays(days).toString();
        String endDate = LocalDate.now().toString();
        String url = String.format(API_URL, startDate, endDate);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response;
        try {
            // Fetch the data from the API
            response = restTemplate.getForEntity(url, Map.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Handle errors during the API call
            System.err.println("Error occurred when calling the earthquake API: " + e.getMessage());
            return new ArrayList<>();
        }

        // Extract the data from the API response
        Map<String, Object> body = response.getBody();
        List<LinkedHashMap<String, Object>> features = (List<LinkedHashMap<String, Object>>) body.get("features");
        List<Earthquake> earthquakes = new ArrayList<>();
        // Process each feature in the API response
        for (Map<String, Object> feature : features) {
            try {
                Map<String, Object> properties = (Map<String, Object>) feature.get("properties");
                String place = (String) properties.get("place");
                String countryInData = place != null ? place.split(",")[place.split(",").length - 1].trim() : "Unknown";

                // Skip if the country does not match
                if (!country.equalsIgnoreCase(countryInData)) {
                    continue;
                }

                // Extract and convert the magnitude
                Object magnitudeObject = properties.get("mag");
                double magnitude = magnitudeObject instanceof Double ? (Double) magnitudeObject : (Integer) magnitudeObject;

                // Convert the time to LocalDateTime
                LocalDateTime dateTime = LocalDateTime.ofInstant(
                        Instant.ofEpochMilli((Long) properties.get("time")),
                        ZoneId.systemDefault());
                // Create a new Earthquake object and add it to the list
                Earthquake earthquake = new Earthquake(country, place, magnitude, dateTime);
                earthquakes.add(earthquake);
            } catch (Exception e) {
                System.err.println("Error processing earthquake data: " + e.getMessage());
            }
        }

        return earthquakes;
    }
}