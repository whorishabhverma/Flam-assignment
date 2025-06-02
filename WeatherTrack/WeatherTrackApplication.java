package com.example.weathertrack;

import android.app.Application;
import androidx.work.Configuration;
import androidx.work.WorkManager;

public class WeatherTrackApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize WorkManager
        Configuration config = new Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build();
        WorkManager.initialize(this, config);
    }
}