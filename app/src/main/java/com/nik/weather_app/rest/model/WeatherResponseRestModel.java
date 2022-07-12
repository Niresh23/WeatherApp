package com.nik.weather_app.rest.model;

import com.google.gson.annotations.SerializedName;

public class WeatherResponseRestModel {
    @SerializedName("coord") public CoordRestModel coordinates;
    @SerializedName("weather") public WeatherRestModel[] weather;
    @SerializedName("base") public String base;
    @SerializedName("main") public MainRestModel main;
    @SerializedName("visibility") public int visibility;
    @SerializedName("wind") public WindRestModel wind;
    @SerializedName("clouds") public CloudsRestModel clouds;
    @SerializedName("dt") public long dt;
    @SerializedName("sys") public SysRestModel sys;
    @SerializedName("id") public long id;
    @SerializedName("timeZone") public int timeZone;
    @SerializedName("name") public String name;
    @SerializedName("cod") public int cod;
}
