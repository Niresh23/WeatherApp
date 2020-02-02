package com.nik.weather_app.cities_db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {City.class}, version = 1, exportSchema = false)
public abstract class CityDatabase extends RoomDatabase {

    public abstract CityDao cityDao();
    private static CityDatabase INSTANCE;
    public static CityDatabase getInstance(Context context) {
        String DATABASE_NAME = "City_DB";
        if(INSTANCE == null) {
            synchronized (CityDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CityDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return INSTANCE;
    }
}
