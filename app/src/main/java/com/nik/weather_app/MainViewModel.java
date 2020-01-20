package com.nik.weather_app;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.nik.weather_app.data.Weather;
import com.nik.weather_app.repository.Repository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private Repository repository;

    public LiveData<Weather> getWeather() {
        return repository.getWeather();
    }

    public void updateWeather(String city) {
        repository.updateWeatherData(city);
    }

    public void connect(@NonNull Context context) {
        repository = Repository.getInstance(context);
    }

    public LiveData<List<String>> getCities() {
        return repository.loadCities();
    }

}
