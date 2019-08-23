package com.nik.weather_app.rest;

import com.nik.weather_app.rest.entities.IOpenWeather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpenWeatherRepo {
    private static OpenWeatherRepo singletone = null;
    private IOpenWeather API;

    private OpenWeatherRepo() {
        API = createAdapter();
    }

    public static OpenWeatherRepo getSingletone() {
        if(singletone == null)
            singletone = new OpenWeatherRepo();

        return singletone;
    }

    public IOpenWeather getAPI() {
        return API;
    }

    private IOpenWeather createAdapter() {
        Retrofit adapter = new Retrofit.Builder().
                baseUrl("http://api.openweathermap.org/").
                addConverterFactory(GsonConverterFactory.create()).
                build();

        return adapter.create(IOpenWeather.class);
    }
}
