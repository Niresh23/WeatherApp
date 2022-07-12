package com.nik.weather_app.rest.model;

import com.google.gson.annotations.SerializedName;

public class MainRestModel {
    @SerializedName("temp") public float temp;
    @SerializedName("feels_like") public float feelsLike;
    @SerializedName("temp_min") public float tempMin;
    @SerializedName("temp_max") public float tempMax;
    @SerializedName("pressure") public float pressure;
    @SerializedName("humidity") public float humidity;
}
