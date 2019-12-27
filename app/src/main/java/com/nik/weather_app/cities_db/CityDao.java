package com.nik.weather_app.cities_db;

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
public interface CityDao {

    @Query("SELECT * FROM City ORDER BY id")
    Single<List<City>> getAll();

    @Query("SELECT * FROM  City WHERE id IN (:cityIds)")
    Single<List<City>> loadByIds(int[] cityIds);

    @Query("SELECT * FROM City WHERE CITY LIKE :city LIMIT 1")
    Single<City> loadByCity(String city);

    @Query("SELECT city FROM City ORDER BY city")
    Single<List<String>> loadCities();

    @Delete
    Single<Integer> delete(City city);

    @Insert
    Completable add(City city);

    @Update
    Completable updateCity(City city);

}
