package com.example.weathertrack.ui.main;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.weathertrack.data.database.WeatherEntity;
import com.example.weathertrack.data.repository.WeatherRepository;

public class MainViewModel extends AndroidViewModel {
    private WeatherRepository repository;
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new WeatherRepository(application);
    }

    public LiveData<WeatherEntity> getLatestWeather() {
        return repository.getLatestWeather();
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void refreshWeather() {
        isLoading.setValue(true);
        repository.fetchAndSaveWeather(new WeatherRepository.WeatherFetchCallback() {
            @Override
            public void onSuccess() {
                isLoading.postValue(false);
                errorMessage.postValue(null);
            }

            @Override
            public void onError(String error) {
                isLoading.postValue(false);
                errorMessage.postValue(error);
            }
        });
    }
}