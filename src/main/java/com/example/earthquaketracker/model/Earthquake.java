package com.example.earthquaketracker.model;

import java.time.LocalDateTime;

public class Earthquake {

    // Fields to store the earthquake data
    private String country;
    private String place;
    private double magnitude;
    private LocalDateTime dateTime;

    // Default constructor
    public Earthquake() {}
    // Getter and setter methods for each field
    public Earthquake(String country, String place, double magnitude, LocalDateTime dateTime) {
        this.country = country;
        this.place = place;
        this.magnitude = magnitude;
        this.dateTime = dateTime;
    }

    public String getCountry() {
        return country.toUpperCase();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}