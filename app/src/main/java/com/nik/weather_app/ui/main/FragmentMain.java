package com.nik.weather_app.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nik.weather_app.MainViewModel;
import com.nik.weather_app.R;
import com.nik.weather_app.data.Weather;
import com.nik.weather_app.databinding.FragmentMainBinding;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {

    private FragmentMainBinding binding;
    private MainViewModel viewModel;
    public FragmentMain() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("FragmentMain", "onCreateView()");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,
                                                                container,false);
        viewModel = ViewModelProviders
                    .of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        LiveData<Weather> ld = Transformations.map(viewModel.getWeather(), this::transformation);
        ld.observe(getActivity(), weather -> {
            Log.d("FragmentMain","binding.setWeather()");
            binding.setWeather(weather);
            }
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private Weather transformation(Weather weather) {
        weather.setIcon(getWeatherIcon(weather.getIconId(), weather.getSunrise(), weather.getSunset()));
        weather.setUpdated(renderDate(weather.getDate()));
        return weather;
    }

    private String renderDate(long date) {
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String updateOn = dateFormat.format(new Date(date * 1000));
        return "Last update: " + updateOn;
    }


    private String getWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800) {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset) {
                icon = getString(R.string.weather_sunny);
            } else {
                icon = getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2: {
                    icon = getString(R.string.weather_thunder);
                    break;
                }
                case 3: {
                    icon = getString(R.string.weather_drizzle);
                    break;
                }
                case 5: {
                    icon = getString(R.string.weather_rainy);
                    break;
                }
                case 6: {
                    icon = getString(R.string.weather_snowy);
                    break;
                }
                case 7: {
                    icon = getString(R.string.weather_foggy);
                    break;
                }
                case 8: {
                    icon = getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        return icon;
    }
}
