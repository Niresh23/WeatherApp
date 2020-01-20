package com.nik.weather_app.cities_db;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class CityDB extends RoomDatabase {
    public abstract CityDao cityDao();
    private static CityDB database;
    public static CityDB getInstance(Context context) {
        String DATABASE_NAME = "CityDatabase";
        if(database == null) {
            database = Room.databaseBuilder(context, CityDB.class, DATABASE_NAME).build();
            return database;
        } else return database;
    }

}
