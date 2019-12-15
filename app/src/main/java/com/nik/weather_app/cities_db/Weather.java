package com.nik.weather_app.cities_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* (SQLiteDatabase database, String city, String country, String description, float humidity,
                              float pressure, float temperature, long update, long icon, long sunrise, long sunset)*/
@Entity
public class Weather {

    @NonNull
    @PrimaryKey
    public int id;

    @NonNull
    @ColumnInfo
    public String city;

    @ColumnInfo
    public String country;

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public int getId() {return id; }

}
