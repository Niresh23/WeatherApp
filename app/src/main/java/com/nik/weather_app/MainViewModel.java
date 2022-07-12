package com.nik.weather_app;

import android.icu.text.RelativeDateTimeFormatter;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nik.weather_app.base.PartOfTheDay;
import com.nik.weather_app.base.WeatherIcon;
import com.nik.weather_app.data.ForecastListener;
import com.nik.weather_app.data.ForecastViewState;
import com.nik.weather_app.data.WeatherViewState;
import com.nik.weather_app.repository.IRepository;
import com.nik.weather_app.rest.model.ForecastResponseModel;
import com.nik.weather_app.rest.model.ForecastRestModel;
import com.nik.weather_app.rest.model.WeatherResponseRestModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.SingleObserver;
import io.reactivex.observers.DisposableSingleObserver;

@HiltViewModel
public class MainViewModel extends ViewModel implements ForecastListener {

    private final MutableLiveData<WeatherViewState> weatherViewStateLiveData = new MutableLiveData<>();

    public MutableLiveData<List<ForecastViewState>> getForecastListViewStateLiveData() {
        return forecastListViewStateLiveData;
    }

    private final MutableLiveData<List<ForecastViewState>> forecastListViewStateLiveData = new MutableLiveData<>();

    private final IRepository repository;

    @Inject
    public MainViewModel(IRepository repository) {
        this.repository = repository;
    }

    private final SingleObserver<WeatherResponseRestModel> weatherDisposable = new DisposableSingleObserver<WeatherResponseRestModel>() {
        @Override
        public void onSuccess(WeatherResponseRestModel weatherResponseRestModel) {
            weatherViewStateLiveData.postValue(renderWeather(weatherResponseRestModel));
        }

        @Override
        public void onError(Throwable e) {
            Log.e("MainViewModel", "Error", e);
        }
    };

    private final SingleObserver<ForecastResponseModel> forecastDisposable = new DisposableSingleObserver<ForecastResponseModel>() {
        @Override
        public void onSuccess(ForecastResponseModel forecastResponseModel) {
            forecastListViewStateLiveData.postValue(renderForecast(forecastResponseModel));
        }

        @Override
        public void onError(Throwable e) {
            Log.e("MainViewModel", "Error", e);
        }
    };

    public LiveData<WeatherViewState> getWeatherViewStateLiveData() {
        return weatherViewStateLiveData;
    }

    public void getWeather(String city, String units) {
        repository.getWeather(city, units).subscribe(weatherDisposable);
    }


    public void getWeather(double lat, double lon, String units) {
        repository.getWeather(lat, lon, units).subscribe(weatherDisposable);
    }

    public void getForecast(double lat, double lon, String units) {
        repository.getForecast(lat, lon, units).subscribe(forecastDisposable);
    }
    public void getForecast(String city, String units) {
        repository.getForecast(city, units).subscribe(forecastDisposable);
    }

    private List<ForecastViewState> renderForecast(ForecastResponseModel model) {
        List<ForecastViewState> list = new ArrayList<>();
        for (ForecastRestModel restModel : model.list) {
            list.add(
                    new ForecastViewState(
                            restModel.main.temp,
                            getWeatherIcon(restModel.weather.get(0).id, PartOfTheDay.getByValue(restModel.sys.pod)),
                            restModel.date * 1000
                    )
            );
        }
        return list;
    }

    private WeatherViewState renderWeather(WeatherResponseRestModel model) {
        return new WeatherViewState(
                model.name,
                model.sys.country,
                model.weather[0].description,
                model.main.humidity,
                model.main.pressure,
                model.main.temp,
                renderDate(model.dt),
                getWeatherIcon(model.weather[0].id, PartOfTheDay.getBySysTime(model.sys.sunrise * 1000L, model.sys.sunset * 1000L))
        );
    }

    @Override
    public void onForecastItemClicked(ForecastViewState forecastModel) {

    }

    private String renderDate(long date) {
        java.text.DateFormat dateFormat = java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT);
        return dateFormat.format(new Date(date * 1000));
    }

    private String getWeatherIcon(int actualId, PartOfTheDay partOfTheDay) {
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800) {
            switch (partOfTheDay) {
                case DAY:
                    return WeatherIcon.SUNNY.icon;
                case NIGHT:
                    return WeatherIcon.CLEAR_NIGHT.icon;
            }
        } else {
            switch (id) {
                case 2: {
                    icon = WeatherIcon.THUNDER.icon;
                    break;
                }
                case 3: {
                    icon = WeatherIcon.DRIZZLE.icon;
                    break;
                }
                case 5: {
                    icon = WeatherIcon.RAINY.icon;
                    break;
                }
                case 6: {
                    icon = WeatherIcon.SNOWY.icon;
                    break;
                }
                case 7: {
                    icon = WeatherIcon.FOGGY.icon;
                    break;
                }
                case 8: {
                    icon = WeatherIcon.CLOUDY.icon;
                    break;
                }
            }
        }
        return icon;
    }
}
