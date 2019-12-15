package com.nik.weather_app;

import android.app.Application;

import androidx.room.Room;

import com.nik.weather_app.cities_db.WeatherDatabase;

public class App extends Application {
    private final String DATABASE_NAME = "Weather Database";
    private WeatherDatabase database;
    private static App INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(),
                                        WeatherDatabase.class,
                                        DATABASE_NAME)
                                        .build();
        INSTANCE = this;
    }

    public static App getIstance(){
        return INSTANCE;
    }

    public WeatherDatabase getDatabase() {
        return database;
    }

}

