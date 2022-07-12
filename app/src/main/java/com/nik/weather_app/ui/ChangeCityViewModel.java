package com.nik.weather_app.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nik.weather_app.repository.IRepository;
import com.nik.weather_app.rest.model.geocoding.GeocodingModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.observers.DisposableSingleObserver;

@HiltViewModel
public class ChangeCityViewModel extends ViewModel {
    private final IRepository repository;
    private final MutableLiveData<List<String>> cityListLiveData = new MutableLiveData<>();

    @Inject
    public ChangeCityViewModel(IRepository repository) {
        this.repository = repository;
    }

    public void findCity(String city) {
        repository.getGeocodingList(city).subscribe(new DisposableSingleObserver<List<GeocodingModel>>() {
            @Override
            public void onSuccess(List<GeocodingModel> geocodingModels) {
                List<String> newList = new ArrayList<>();

                for(GeocodingModel model : geocodingModels) {
                    newList.add(model.name + "," + model.country + "," + model.state);
                }

                cityListLiveData.postValue(newList);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public LiveData<List<String>> getCityListLiveData() {
        return cityListLiveData;
    }
}
