package com.example.weathertrack.worker;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.example.weathertrack.data.repository.WeatherRepository;

public class WeatherSyncWorker extends Worker {

    public WeatherSyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        WeatherRepository repository = new WeatherRepository(getApplicationContext());

        final boolean[] success = {false};
        final Object lock = new Object();

        repository.fetchAndSaveWeather(new WeatherRepository.WeatherFetchCallback() {
            @Override
            public void onSuccess() {
                synchronized (lock) {
                    success[0] = true;
                    lock.notify();
                }
            }

            @Override
            public void onError(String error) {
                synchronized (lock) {
                    success[0] = false;
                    lock.notify();
                }
            }
        });

        // Wait for async operation to complete
        synchronized (lock) {
            try {
                lock.wait(30000); // 30 second timeout
            } catch (InterruptedException e) {
                return Result.failure();
            }
        }

        return success[0] ? Result.success() : Result.retry();
    }
}