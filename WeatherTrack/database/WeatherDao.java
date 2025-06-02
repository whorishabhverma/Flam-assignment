package com.example.weathertrack.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface WeatherDao {
    @Insert
    void insertWeatherRecord(WeatherEntity weather);

    @Query("SELECT * FROM weather_records ORDER BY timestamp DESC LIMIT 1")
    LiveData<WeatherEntity> getLatestWeather();

    @Query("SELECT * FROM weather_records WHERE timestamp >= :startTime ORDER BY timestamp DESC")
    LiveData<List<WeatherEntity>> getWeatherFromTimestamp(long startTime);

    @Query("SELECT * FROM weather_records ORDER BY timestamp DESC")
    LiveData<List<WeatherEntity>> getAllWeatherRecords();

    @Query("DELETE FROM weather_records WHERE timestamp < :cutoffTime")
    void deleteOldRecords(long cutoffTime);
}