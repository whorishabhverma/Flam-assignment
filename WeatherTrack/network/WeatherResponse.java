package com.example.weathertrack.data.network;

public class WeatherResponse {
    private double temperature;
    private int humidity;
    private String condition;
    private String city;

    public WeatherResponse(double temperature, int humidity, String condition, String city) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.condition = condition;
        this.city = city;
    }

    // Getters
    public double getTemperature() { return temperature; }
    public int getHumidity() { return humidity; }
    public String getCondition() { return condition; }
    public String getCity() { return city; }
}