package com.nik.weather_app.ui.setting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.nik.weather_app.MainViewModel;
import com.nik.weather_app.R;

import java.util.List;
import java.util.Objects;


public class SettingFragment extends Fragment implements View.OnClickListener {

    private List<String> cities;
    private Spinner spinner;
    private MainViewModel viewModel;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_add_city).setOnClickListener(this);
        view.findViewById(R.id.btn_clear_DB).setOnClickListener(this);

        spinner = view.findViewById(R.id.spinner_cities);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_city:
                showInputDialog();
                break;
            case R.id.btn_clear_DB:
        }
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle(R.string.action_change_city);
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String currentCity = input.getText().toString().toUpperCase();
            if(currentCity.length() > 1) {
            }
        });
        builder.show();
    }
}
