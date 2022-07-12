package com.nik.weather_app.rest.model;

import com.google.gson.annotations.SerializedName;

public class WindRestModel {
    @SerializedName("speed") public float speed;
    @SerializedName("deg") public float deg;
}
