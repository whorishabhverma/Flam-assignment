// Updated WeeklyActivity.java to use custom chart
package com.example.weathertrack.ui.weekly;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.weathertrack.R;
import com.example.weathertrack.data.database.WeatherEntity;
import com.example.weathertrack.ui.views.SimpleLineChart;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeeklyActivity extends AppCompatActivity {
    private SimpleLineChart temperatureChart;
    private WeeklyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly);

        temperatureChart = findViewById(R.id.temperature_chart);
        setupViewModel();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(WeeklyViewModel.class);
        viewModel.getWeeklyWeather().observe(this, this::updateChart);
    }

    private void updateChart(List<WeatherEntity> weatherList) {
        if (weatherList == null || weatherList.isEmpty()) {
            return;
        }

        List<Float> temperatures = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd", Locale.getDefault());

        // Reverse to show chronological order
        for (int i = weatherList.size() - 1; i >= 0; i--) {
            WeatherEntity weather = weatherList.get(i);
            temperatures.add((float) weather.getTemperature());
            dates.add(sdf.format(new Date(weather.getTimestamp())));
        }

        temperatureChart.setData(temperatures, dates);
    }
}