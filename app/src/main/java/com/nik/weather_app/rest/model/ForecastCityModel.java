package com.nik.weather_app.rest.model;

import com.google.gson.annotations.SerializedName;

public class ForecastCityModel {
    public String id;
    public String name;
    public CoordRestModel coord;
    @SerializedName("country") public String countryCode;
    public int population;
    public long sunrise;
    public long sunset;
}
