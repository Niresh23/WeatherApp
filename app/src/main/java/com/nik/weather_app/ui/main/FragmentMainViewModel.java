package com.nik.weather_app.ui.main;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nik.weather_app.data.Weather;

public class FragmentMainViewModel extends ViewModel {

    private MutableLiveData<Weather> liveDataWeather = new MutableLiveData<>();

    public void setWeather(Weather weather) {
        Log.d("FragmentMainViewModel", "setWeather()");
        this.liveDataWeather.setValue(weather);
    }

    public MutableLiveData<Weather> getLiveDataWeather() {
        Log.d("FragmentMainViewModel", "getLiveDataWeather");
        return liveDataWeather;
    }
}
