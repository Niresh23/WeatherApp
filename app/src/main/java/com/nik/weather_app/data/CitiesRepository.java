package com.nik.weather_app.data;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class CitiesRepository {
    private static CitiesRepository repository;

    private List<String> cities = new ArrayList<>();

    private MutableLiveData<List<String>> liveData = new MutableLiveData<>();

    private CitiesRepository() {
        cities.add(0, "");
        cities.add(1, "Another name...");
        cities.add("Moscow");
        cities.add("Novosibirsk");
        cities.add("Tomsk");
        liveData.setValue(cities);
        Log.d("CitiesRepository","Consctructor()");
    }

    public static CitiesRepository getRepository() {
        if(repository == null) return new CitiesRepository();
        else return repository;
    }

    public void addCity(String city) {
        cities.add(city);
    }

    public MutableLiveData<List<String>> getLiveData() {
        return liveData;
    }

    public ArrayList<String> getCitiesList() {
        ArrayList<String> clone = new ArrayList<>();
        for (String c: cities) {
            clone.add(c);
        }
        clone.trimToSize();
        return clone;
    }
 }
