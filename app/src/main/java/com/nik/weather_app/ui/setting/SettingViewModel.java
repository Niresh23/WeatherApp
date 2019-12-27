package com.nik.weather_app.ui.setting;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;


import com.nik.weather_app.data.CitiesRepository;
import java.util.List;


public class SettingViewModel extends ViewModel {

    private MutableLiveData<SettingViewState> viewStateLiveData = new MutableLiveData<>();
    private LiveData<List<String>> repositoryLiveData = CitiesRepository.getRepository().getLiveData();
    private final Observer<List<String>> observer = strings -> {
        Log.d("SettingViewModel","onChanged()");
        viewStateLiveData.setValue(new SettingViewState(strings));
    };

    public SettingViewModel() {
        Log.d("SettingViewModel","Constructor()");
        repositoryLiveData.observeForever(observer);
}


    public MutableLiveData<SettingViewState> getLiveData() {
        return viewStateLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repositoryLiveData.removeObserver(observer);
    }
}
