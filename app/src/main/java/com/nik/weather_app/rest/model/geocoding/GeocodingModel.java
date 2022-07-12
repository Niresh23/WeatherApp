package com.nik.weather_app.rest.model.geocoding;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class GeocodingModel {
    public String name;
    @SerializedName("local_names") public Map<String, String> listName;
    public float lat;
    public float lon;
    public String country;
    public String state;
}
