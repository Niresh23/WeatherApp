package com.nik.weather_app.repository;

import android.content.Context;
import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nik.weather_app.cities_db.City;
import com.nik.weather_app.cities_db.CityDatabase;
import com.nik.weather_app.data.Weather;
import com.nik.weather_app.rest.OpenWeatherRepo;
import com.nik.weather_app.rest.entities.WeatherRequestRestModel;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository INSTANCE = null;
    public static Repository getInstance(Context context) {
        if(INSTANCE == null)
            INSTANCE = new Repository(context);
        return INSTANCE;
    }

    private CityDatabase database;
    private Weather weather = new Weather();
    private MutableLiveData<Weather> liveData = new MutableLiveData<>();
    private Context context;


    private Repository(Context context) {
        this.context = context;
        database = CityDatabase.getInstance(context);
        liveData.setValue(weather);
    }

    public LiveData<List<String>> loadCities(){
        MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        database.cityDao().loadCities().subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                Log.d("Repository", "loadCities().onSuccess(), " + strings.get(0));
                liveData.postValue(strings);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
        return liveData;
    }

    public void addCity(City city) {
        database.cityDao().add(city).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) { }
            @Override
            public void onComplete() {
                Log.d("Repository", "addCity().onComplete()");
            }
            @Override
            public void onError(Throwable e) { e.printStackTrace(); }
        });
    }

    public void delete(City city) {
        database.cityDao().delete(city)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void updateWeatherData(final String city) {
        City town = new City();
        town.setName(city);
        addCity(town);
        OpenWeatherRepo.getSingletone().getAPI().loadWeather(city + ",ru",
                "762ee61f52313fbd10a4eb54ae4d4de2", "metric")
                .enqueue(new Callback<WeatherRequestRestModel>() {
                    @Override
                    public void onResponse(Call<WeatherRequestRestModel> call, Response<WeatherRequestRestModel> response) {
                        if(response.body() != null && response.isSuccessful()) {
                            renderWeather(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<WeatherRequestRestModel> call, Throwable t) {

                    }
                });
    }

    public LiveData<Weather> getWeather() {
        return liveData;
    }

    private void renderWeather(WeatherRequestRestModel model) {
        Log.d("Repository", "renderWeather()");
        weather.setCity(model.name);
        weather.setCountry(model.sys.country);
        weather.setDescription(model.weather[0].description);
        weather.setHumidity(model.main.humidity);
        weather.setPressure(model.main.pressure);
        weather.setTemperature(model.main.temp);
        weather.setDate(model.dt);
        weather.setIconId(model.weather[0].id);
        weather.setSunrise(model.sys.sunrise);
        weather.setSunset(model.sys.sunset);
        liveData.setValue(weather);
    }

}
