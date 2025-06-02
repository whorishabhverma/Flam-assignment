package com.example.weathertrack.data.network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherApiService {
    @GET("weather/current")
    Call<WeatherResponse> getCurrentWeather();
}

// Mock implementation class
public class MockWeatherApiService {
    private static final String[] CONDITIONS = {"Sunny", "Cloudy", "Rainy", "Snowy", "Stormy"};
    private static final String[] CITIES = {"New York", "London", "Tokyo", "Sydney", "Mumbai"};

    public static WeatherResponse getMockWeather() {
        // Generate random weather data
        double temperature = 15 + (Math.random() * 20); // 15-35°C
        int humidity = 30 + (int)(Math.random() * 60); // 30-90%
        String condition = CONDITIONS[(int)(Math.random() * CONDITIONS.length)];
        String city = CITIES[(int)(Math.random() * CITIES.length)];

        return new WeatherResponse(temperature, humidity, condition, city);
    }
}