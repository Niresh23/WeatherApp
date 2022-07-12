package com.nik.weather_app.repository;

import com.nik.weather_app.rest.OpenWeatherAPI;
import com.nik.weather_app.rest.model.ForecastResponseModel;
import com.nik.weather_app.rest.model.WeatherResponseRestModel;
import com.nik.weather_app.rest.model.geocoding.GeocodingModel;

import java.util.List;

import javax.inject.Inject;
import io.reactivex.Single;

public class RepositoryImpl implements IRepository {

    private final OpenWeatherAPI openWeatherAPI;

    @Inject
    public RepositoryImpl(OpenWeatherAPI openWeatherAPI) {
        this.openWeatherAPI = openWeatherAPI;
    }

    @Override
    public Single<WeatherResponseRestModel> getWeather(String city, String units) {
        return openWeatherAPI.loadWeather(city, units);
    }

    @Override
    public Single<WeatherResponseRestModel> getWeather(double lat, double lon, String units) {
        return openWeatherAPI.loadWeather(lat, lon, units);
    }

    @Override
    public Single<ForecastResponseModel> getForecast(String city, String metric) {
        return openWeatherAPI.loadForecast(city, metric);
    }

    @Override
    public Single<ForecastResponseModel> getForecast(double lat, double lon, String metric) {
        return openWeatherAPI.loadForecast(lat, lon, metric);
    }

    @Override
    public Single<List<GeocodingModel>> getGeocodingList(String city) {
        return openWeatherAPI.findCity(city, 100);
    }
}
