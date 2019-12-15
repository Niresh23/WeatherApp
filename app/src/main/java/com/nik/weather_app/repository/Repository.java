package com.nik.weather_app.repository;

import android.app.Application;

import androidx.room.RoomDatabase;

public class Repository {

    private RoomDatabase database;

    public Repository(RoomDatabase database) {
        this.database = database;
    }



}
