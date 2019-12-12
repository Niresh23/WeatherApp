package com.nik.weather_app.room_database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


@Dao
public interface WeatherDao {

    @Query("SELECT * FROM weather ORDER BY id")
    LiveData<List<Weather>> getAll();

    @Query("SELECT * FROM  weather WHERE id IN (:weatherIds)")
    LiveData<List<Weather>> loadByIds(int[] weatherIds);

    @Query("SELECT * FROM weather WHERE CITY LIKE :city LIMIT 1")
    LiveData<Weather> loadByCity(String city);

    @Query("SELECT city FROM weather ORDER BY city")
    LiveData<List<String>> loadCities();

    @Delete
    Single<Integer> delete(Weather weather);

    @Insert
    Completable add(Weather weather);

    @Update
    Completable updateWeather(Weather weather);

}
