package com.nik.weather_app.ui.setting;

import android.util.Log;

import java.util.List;

public class SettingViewState {
    private List<String> cities;
    public SettingViewState(List<String> cities) {
        this.cities = cities;
        Log.d("SettingViewState","Consctructor()");
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getCities() {
        return cities;
    }
}
