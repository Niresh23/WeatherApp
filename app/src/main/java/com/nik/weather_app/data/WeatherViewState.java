package com.nik.weather_app.data;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import java.util.Locale;

public class WeatherViewState extends BaseObservable {

    public WeatherViewState(String city, String country, String description, float humidity, float pressure, float temperature, String updated, String icon) {
        this.city = city;
        this.country = country;
        this.description = description;
        this.humidity = renderHumidity(humidity);
        this.pressure = renderPressure(pressure);
        this.temperature = renderTemperature(temperature);
        this.updated = updated;
        this.icon = icon;
    }

    private final String city;
    private final String country;
    private final String description;
    private final String humidity;
    private final String pressure;
    private final String temperature;
    private final String updated;
    private final String icon;

    @Bindable
    public String getCity() {
        return this.city;
    }

    @Bindable
    public String getCountry() {
        return country;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public String getHumidity() {
        return humidity;
    }

    public String renderHumidity(float humidity) {
        return humidity + "%";
    }

    @Bindable
    public String getPressure() {
        return pressure;
    }

    public String renderPressure(float pressure) {
        return pressure + "hPa";
    }

    @Bindable
    public String getTemperature() {
        return temperature;
    }

    private String renderTemperature(float temperature) {
        return String.format(Locale.getDefault(), "%.0f", temperature)
                + "\u2103";
    }

    @Bindable
    public String getUpdated() {
        return updated;
    }

    @Bindable
    public String getIcon() {
        return icon;
    }

}
