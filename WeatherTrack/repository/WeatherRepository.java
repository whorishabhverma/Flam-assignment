package com.example.weathertrack.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.example.weathertrack.data.database.WeatherDao;
import com.example.weathertrack.data.database.WeatherDatabase;
import com.example.weathertrack.data.database.WeatherEntity;
import com.example.weathertrack.data.network.MockWeatherApiService;
import com.example.weathertrack.data.network.WeatherResponse;
import com.example.weathertrack.utils.NetworkUtils;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherRepository {
    private WeatherDao weatherDao;
    private ExecutorService executor;
    private Context context;

    public WeatherRepository(Context context) {
        this.context = context;
        WeatherDatabase database = WeatherDatabase.getInstance(context);
        weatherDao = database.weatherDao();
        executor = Executors.newFixedThreadPool(2);
    }

    public LiveData<WeatherEntity> getLatestWeather() {
        return weatherDao.getLatestWeather();
    }

    public LiveData<List<WeatherEntity>> getWeeklyWeather() {
        long oneWeekAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L);
        return weatherDao.getWeatherFromTimestamp(oneWeekAgo);
    }

    public interface WeatherFetchCallback {
        void onSuccess();
        void onError(String error);
    }

    public void fetchAndSaveWeather(WeatherFetchCallback callback) {
        executor.execute(() -> {
            try {
                if (!NetworkUtils.isNetworkAvailable(context)) {
                    callback.onError("No internet connection");
                    return;
                }

                // Simulate API call with mock data
                WeatherResponse response = MockWeatherApiService.getMockWeather();

                WeatherEntity entity = new WeatherEntity(
                        response.getTemperature(),
                        response.getHumidity(),
                        response.getCondition(),
                        System.currentTimeMillis(),
                        response.getCity()
                );

                weatherDao.insertWeatherRecord(entity);
                callback.onSuccess();

            } catch (Exception e) {
                callback.onError("Failed to fetch weather: " + e.getMessage());
            }
        });
    }

    public void cleanupOldRecords() {
        executor.execute(() -> {
            long cutoffTime = System.currentTimeMillis() - (30 * 24 * 60 * 60 * 1000L); // 30 days
            weatherDao.deleteOldRecords(cutoffTime);
        });
    }
}