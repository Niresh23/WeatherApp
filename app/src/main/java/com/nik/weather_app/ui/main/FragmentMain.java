package com.nik.weather_app.ui.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nik.weather_app.R;
import com.nik.weather_app.databinding.FragmentMainBinding;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {

    private FragmentMainBinding binding;
    private FragmentMainViewModel viewModel;
    public FragmentMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("FragmentMain", "onCreateView()");
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,
                                                                container,false);
        viewModel = ViewModelProviders.of(this).get(FragmentMainViewModel.class);
        viewModel.getLiveDataWeather().observeForever( weather -> {
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

}
