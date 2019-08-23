package com.nik.weather_app.rest.entities;

import com.google.gson.annotations.SerializedName;

public class CoordRestModel {
    @SerializedName("lon") public float lon;
    @SerializedName("lat") public float lat;

}
