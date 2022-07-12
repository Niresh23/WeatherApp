package com.nik.weather_app.rest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastRestModel {
    @SerializedName("dt") public long date;
    public MainRestModel main;
    public List<WeatherRestModel> weather;
    public CloudsRestModel clouds;
    public WindRestModel wind;
    public float visibility;
    public float pop;
    public RainRestModel rain;
    public SnowRestModel snow;
    public ForecastSysModel sys;
    @SerializedName("dt_txt") public String dateTxt;
}
