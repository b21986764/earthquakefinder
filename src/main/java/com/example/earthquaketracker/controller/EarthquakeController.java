package com.example.earthquaketracker.controller;

import com.example.earthquaketracker.model.Earthquake;
import com.example.earthquaketracker.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller // Indicates that this class is a Controller in the MVC architecture.
public class EarthquakeController {

    private final EarthquakeService earthquakeService;

    @Autowired
    public EarthquakeController(EarthquakeService earthquakeService) {
        this.earthquakeService = earthquakeService;
    }

    // Mapping for the home page of the website.
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Mapping for the "/earthquakes" URL.
    // Accepts two parameters from the request: country and days.
    // Returns the earthquakes view populated with a list of earthquakes based on the provided parameters.
    @GetMapping("/earthquakes")
    public String getEarthquakes(@RequestParam String country, @RequestParam int days, Model model) {

        // Fetch the earthquakes data.
        List<Earthquake> earthquakes = earthquakeService.getEarthquakes(country, days);

        // Add the data to the model to be used by the view.
        model.addAttribute("country", country);
        model.addAttribute("days", days);
        model.addAttribute("earthquakes", earthquakes);

        // Return the name of the view. The data in the model will be available in the view.
        return "earthquakes";
    }
}
