package com.nik.weather_app.ui.main;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
    private TextView cityTextView;
    private TextView detailsTextView;
    private TextView currentTemperatureTextView;
    private TextView updateTextView;
    private TextView weatherIconTextView;

    private GetDataListener mGetDataListener;
    FragmentMainBinding binding;
    FragmentMainViewModel viewModel;

    public FragmentMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,
                                                                container,false);
        viewModel = ViewModelProviders.of(this).get(FragmentMainViewModel.class);

        if(savedInstanceState == null) mGetDataListener.getData();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mGetDataListener.getData();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            mGetDataListener = (GetDataListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    "GetDataListener interface");
        }
        super.onAttach(context);
    }

    public interface GetDataListener {
        void getData();
    }
}
