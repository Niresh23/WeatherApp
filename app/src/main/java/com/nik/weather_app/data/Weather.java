package com.nik.weather_app.data;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

import java.util.Locale;

public class Weather extends BaseObservable {

    private  String city = "1";
    private  String country = "1";
    private  String description = "1";
    private  String humidity = "1";
    private  String pressure = "1";
    private  String temperature = "1";
    private  String updated = "1";
    private  String icon = "1";

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
        notifyPropertyChanged(BR.country);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity + "%";
        notifyPropertyChanged(BR.humidity);
    }

    @Bindable
    public String getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        Log.d("Weather", "setPressure()" + pressure);
        this.pressure = pressure + "hPa";
        notifyPropertyChanged(BR.pressure);
    }

    @Bindable
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = String.format(Locale.getDefault(), "%.2f", temperature)
                + "\u2103";
        notifyPropertyChanged(BR.temperature);
    }

    @Bindable
    public String getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = String.valueOf(updated);
        notifyPropertyChanged(BR.updated);
    }

    @Bindable
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        notifyPropertyChanged(BR.icon);
    }
}
