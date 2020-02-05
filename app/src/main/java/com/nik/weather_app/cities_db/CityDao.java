package com.nik.weather_app.cities_db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface CityDao {

    @Query("SELECT * FROM City ORDER BY cityId")
    Single<List<City>> getAll();

    @Query("SELECT * FROM  City WHERE cityId IN (:cityIds)")
    Single<List<City>> loadByIds(int[] cityIds);

    @Query("SELECT cityName FROM City")
    Single<List<String>> loadCities();

    @Delete
    Single<Integer> delete(City city);

    @Query("DELETE FROM City")
    Completable deleteAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(City city);

    @Update
    Completable updateCity(City city);
}
