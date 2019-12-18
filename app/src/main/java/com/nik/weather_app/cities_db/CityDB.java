package com.nik.weather_app.cities_db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class CityDB extends RoomDatabase {
    public abstract CityDao cityDao();

    public static CityDB getInstance(Application application) {
        String DATABASE_NAME = "CityDatabase";
        return Room.databaseBuilder(application, CityDB.class, DATABASE_NAME).build();
    }

}
