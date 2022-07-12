package com.nik.weather_app.repository;

import com.nik.weather_app.rest.model.ForecastResponseModel;
import com.nik.weather_app.rest.model.WeatherResponseRestModel;
import com.nik.weather_app.rest.model.WeatherRestModel;
import com.nik.weather_app.rest.model.geocoding.GeocodingModel;

import java.util.List;

import io.reactivex.Single;

public interface IRepository {
    Single<WeatherResponseRestModel> getWeather(String city, String units);
    Single<WeatherResponseRestModel> getWeather(double lat, double lon, String units);
    Single<ForecastResponseModel> getForecast(String city, String units);
    Single<ForecastResponseModel> getForecast(double lat, double lon, String units);
    Single<List<GeocodingModel>> getGeocodingList(String city);
}
