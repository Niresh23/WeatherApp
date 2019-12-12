package com.nik.weather_app.room_database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Weather.class}, version = 1, exportSchema = false)
public abstract class WeatherDatabase extends RoomDatabase {
    public abstract WeatherDao weatherDao();
}
