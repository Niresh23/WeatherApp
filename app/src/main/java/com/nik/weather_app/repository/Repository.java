package com.nik.weather_app.repository;

import android.app.Application;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nik.weather_app.cities_db.City;
import com.nik.weather_app.cities_db.CityDB;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Repository {


    private CityDB database;
    private Application application;

    public Repository(Application application) {
        this.application = application;
        database = CityDB.getInstance(application);
    }

    public LiveData<List<String>> loadCities(){
        MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        database.cityDao().loadCities().subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<String>>() {
            @Override
            public void onSuccess(List<String> strings) {
                liveData.setValue(strings);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
        });
        return liveData;
    }

    public void addCity(City city) {
        database.cityDao().add(city).subscribeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }
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

}
