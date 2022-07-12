package com.nik.weather_app.rest;

import com.nik.weather_app.rest.model.ForecastResponseModel;
import com.nik.weather_app.rest.model.WeatherResponseRestModel;
import com.nik.weather_app.rest.model.geocoding.GeocodingModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherAPI {
    @GET("data/2.5/weather")
    Single<WeatherResponseRestModel> loadWeather(@Query("q") String city,
                                                 @Query("units") String units);

    @GET("data/2.5/weather")
    Single<WeatherResponseRestModel> loadWeather(@Query("lat") double lat,
                                                 @Query("lon") double lon,
                                                 @Query("units") String units);

    @GET("data/2.5/forecast")
    Single<ForecastResponseModel> loadForecast(@Query("lat") double lat,
                                               @Query("lon") double lon,
                                               @Query("units") String units);

    @GET("data/2.5/forecast")
    Single<ForecastResponseModel> loadForecast(@Query("q") String city,
                                                  @Query("units") String units);

    @GET("geo/1.0/direct")
    Single<List<GeocodingModel>> findCity(@Query("q") String city, @Query("limit") int limit);
}
