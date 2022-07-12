package com.nik.weather_app.data;

public class ForecastViewState implements ListAdapterItem {

    private final String temperature;
    private final String icon;
    private final String date;

    public ForecastViewState(float temperature, String icon, long date) {
        this.temperature = formatTemperature(temperature);
        this.icon = icon;
        this.date = formatDate(date);
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIcon() {
        return icon;
    }

    public String getDate() {
        return date;
    }

    private String formatDate(long date) {
        return android.text.format.DateFormat.format("d MMM", date).toString();
    }

    private String formatTemperature(float temperature) {
        return (int) temperature + "\u2103";
    }
}
