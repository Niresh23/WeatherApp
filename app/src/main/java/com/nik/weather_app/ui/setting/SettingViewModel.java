package com.nik.weather_app.ui.setting;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SettingViewModel extends ViewModel {
    private MutableLiveData<List<String>> cities;
    public MutableLiveData<List<String>> getCities() {
        if(cities == null) cities = new MutableLiveData<>();
        return cities;
    }

}
