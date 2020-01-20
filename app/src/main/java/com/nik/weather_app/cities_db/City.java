package com.nik.weather_app.cities_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/* (SQLiteDatabase database, String name, String country, String description, float humidity,
                              float pressure, float temperature, long update, long icon, long sunrise, long sunset)*/
@Entity
public class City {

    @NonNull
    @PrimaryKey
    public int id;

    @NonNull
    @ColumnInfo
    public String name;

    @ColumnInfo
    public String country;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getId() {return id; }

}
