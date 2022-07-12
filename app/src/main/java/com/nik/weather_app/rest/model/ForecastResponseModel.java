package com.nik.weather_app.rest.model;

import java.util.List;

public class ForecastResponseModel {
    public String cod;
    public String message;
    public int cnt;
    public List<ForecastRestModel> list;
    public ForecastCityModel city;
}
