package com.nik.weather_app.base;

public enum WeatherIcon {
    SUNNY(0xf00d),
    CLEAR_NIGHT(0xf02e),
    FOGGY(0xf014),
    CLOUDY(0xf013),
    RAINY(0xf019),
    SNOWY(0xf01b),
    THUNDER(0xf01e),
    DRIZZLE(0xf01c);

    public final String icon;
    WeatherIcon(int icon) {
        this.icon = Character.toString((char) icon);
    }

}
