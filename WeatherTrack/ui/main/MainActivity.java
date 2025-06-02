package com.example.weathertrack.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import com.example.weathertrack.R;
import com.example.weathertrack.ui.weekly.WeeklyActivity;
import com.example.weathertrack.worker.WeatherSyncWorker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private TextView temperatureText, humidityText, conditionText, cityText, lastUpdatedText;
    private Button refreshButton, weeklyButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupViewModel();
        setupBackgroundSync();
        setupClickListeners();
    }

    private void initViews() {
        temperatureText = findViewById(R.id.temperature_text);
        humidityText = findViewById(R.id.humidity_text);
        conditionText = findViewById(R.id.condition_text);
        cityText = findViewById(R.id.city_text);
        lastUpdatedText = findViewById(R.id.last_updated_text);
        refreshButton = findViewById(R.id.refresh_button);
        weeklyButton = findViewById(R.id.weekly_button);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getLatestWeather().observe(this, weather -> {
            if (weather != null) {
                updateWeatherDisplay(weather);
            }
        });

        viewModel.getErrorMessage().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            refreshButton.setEnabled(!isLoading);
        });
    }

    private void updateWeatherDisplay(WeatherEntity weather) {
        temperatureText.setText(String.format("%.1f°C", weather.getTemperature()));
        humidityText.setText(String.format("%d%%", weather.getHumidity()));
        conditionText.setText(weather.getCondition());
        cityText.setText(weather.getCity());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());
        lastUpdatedText.setText("Last updated: " + sdf.format(new Date(weather.getTimestamp())));
    }

    private void setupBackgroundSync() {
        PeriodicWorkRequest syncWorkRequest = new PeriodicWorkRequest.Builder(
                WeatherSyncWorker.class, 6, TimeUnit.HOURS)
                .build();

        WorkManager.getInstance(this).enqueue(syncWorkRequest);
    }

    private void setupClickListeners() {
        refreshButton.setOnClickListener(v -> viewModel.refreshWeather());
        weeklyButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, WeeklyActivity.class);
            startActivity(intent);
        });
    }
}