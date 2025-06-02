package com.example.weathertrack.ui.weekly;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.weathertrack.data.database.WeatherEntity;
import com.example.weathertrack.data.repository.WeatherRepository;
import java.util.List;

public class WeeklyViewModel extends AndroidViewModel {
    private WeatherRepository repository;

    public WeeklyViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
    }

    public LiveData<List<WeatherEntity>> getWeeklyWeather() {
        return repository.getWeeklyWeather();
    }
}