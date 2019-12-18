package com.nik.weather_app.repository;

import android.app.Application;

import androidx.room.RoomDatabase;

import com.nik.weather_app.cities_db.CityDB;

public class Repository {

    private RoomDatabase database;
    private Application application;

    public Repository(Application application) {
        this.application = application;
        database = CityDB.getInstance(application);
    }



}
