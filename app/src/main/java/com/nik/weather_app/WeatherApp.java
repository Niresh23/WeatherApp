package com.nik.weather_app;


import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class WeatherApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
