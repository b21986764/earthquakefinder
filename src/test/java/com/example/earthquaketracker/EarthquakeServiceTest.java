package com.example.earthquaketracker;
import com.example.earthquaketracker.service.EarthquakeService;
import com.example.earthquaketracker.model.Earthquake;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class EarthquakeServiceTest {

    @Test
    void getEarthquakes_ValidCountryCode() {
        EarthquakeService service = new EarthquakeService();
        List<Earthquake> earthquakes = service.getEarthquakes("ca", 7); // California

        // assert that the list is not empty
        assertFalse(earthquakes.isEmpty());
    }

    @Test
    void getEarthquakes_InvalidCountryCode() {
        EarthquakeService service = new EarthquakeService();
        List<Earthquake> earthquakes = service.getEarthquakes("invalid", 7);

        // assert that the list is empty
        assertTrue(earthquakes.isEmpty());
    }
}