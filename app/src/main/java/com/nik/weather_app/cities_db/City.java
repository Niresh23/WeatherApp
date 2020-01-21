package com.nik.weather_app.cities_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

/* (SQLiteDatabase database, String name, String country, String description, float humidity,
                              float pressure, float temperature, long update, long icon, long sunrise, long sunset)*/
@Entity
public class City {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "cityId")
    private String mId;

    @NonNull
    @ColumnInfo(name = "cityName")
    private String mName;

    @ColumnInfo(name = "countryName")
    private String mCountry;

    public City(@NonNull String cityName) {
        mId = UUID.randomUUID().toString();
        mName = cityName;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getId() {
        return mId;
    }

}
