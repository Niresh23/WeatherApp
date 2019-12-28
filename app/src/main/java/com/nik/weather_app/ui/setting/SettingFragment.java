package com.nik.weather_app.ui.setting;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.transition.CircularPropagation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.nik.weather_app.R;
import com.nik.weather_app.data.CitiesRepository;
import com.nik.weather_app.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SettingFragment extends Fragment {

    private OnCitySelectedListener mOnCitySelectedListener;
    private List<String> cities;
    private Spinner spinner;
    private SettingViewModel viewModel;
    private Repository repository;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        repository = Repository.getInstance(Objects.requireNonNull(getActivity()).getApplication());
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("SettingFragment", "onViewCreated()");
        repository.loadCities().observe(getViewLifecycleOwner(), strings -> cities = strings);
//        viewModel = ViewModelProviders.of(this).get(SettingViewModel.class);
//        viewModel.getLiveData().observeForever(settingViewState -> {
//                Log.d("onChanged()", "done");
//                cities = settingViewState.getCities();
//        });
        if(cities == null) {
            cities = new ArrayList<>();
            cities.add("null!!!");
        }
        spinner = view.findViewById(R.id.spinner_cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                        android.R.layout.simple_spinner_item, cities);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mOnCitySelectedListener.citySelected(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mOnCitySelectedListener = (OnCitySelectedListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement" +
                                            "OnCitySelected interface");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        spinner.setSelection(cities.size());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("SettingFragment", "onViewCreated()");
    }

    public interface OnCitySelectedListener {
        void citySelected(int cityID);
    }

}
