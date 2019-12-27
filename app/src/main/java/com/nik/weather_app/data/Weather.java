package com.nik.weather_app.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.Locale;

public class Weather extends BaseObservable {

    private  String city;
    private  String country;
    private  String description;
    private  String humidity;
    private  String pressure;
    private  String temperature;
    private  String updated;
    private  String icon;

    @Bindable
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
        notifyPropertyChanged(BR.city);
    }
    @Bindable
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity + "%";
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure + "hPa";
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = String.format(Locale.getDefault(), "%.2f", temperature)
                + "\u2103";
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = String.valueOf(updated);
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
