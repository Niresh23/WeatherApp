package com.nik.weather_app.room_database;

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

    @ColumnInfo
    public String description;

    @ColumnInfo
    public double humidity;

    @ColumnInfo
    public double pressure;

    @ColumnInfo
    public double temperature;

    @ColumnInfo
    public long icon;

    @ColumnInfo
    public long sunrise;

    @ColumnInfo
    public long sunset;

    public void setId(int id) {
        this.id = id;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setIcon(long icon) {
        this.icon = icon;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        return description;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public long getIcon() {
        return icon;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

}
