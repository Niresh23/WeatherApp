package com.nik.weather_app.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.nik.weather_app.MainViewModel;
import com.nik.weather_app.R;
import com.nik.weather_app.data.ForecastAdapter;
import com.nik.weather_app.databinding.FragmentMainBinding;
import com.nik.weather_app.ui.ChangeCityBottomFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMain extends Fragment {

    private FragmentMainBinding binding;

    private MainViewModel viewModel;

    private ChangeCityBottomFragment bottomFragment = new ChangeCityBottomFragment();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("FragmentMain", "onCreateView()");
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_main,
                container,
                false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getWeatherViewStateLiveData().observe(getViewLifecycleOwner(), weatherViewState -> {
            binding.setWeatherViewState(weatherViewState);
        });

        viewModel.getForecastListViewStateLiveData().observe(getViewLifecycleOwner(), list -> {
            LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
            binding.forecastRecyclerView.setLayoutManager(layoutManager);
            binding.forecastRecyclerView.setAdapter(new ForecastAdapter(list, viewModel));
        });

        binding.editLocationBtn.setOnClickListener(onClickView -> {
            bottomFragment.show(getParentFragmentManager(), "TAG");
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }
}
