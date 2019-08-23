package com.nik.weather_app;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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

    public FragmentMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cityTextView = view.findViewById(R.id.text_view_city);
        detailsTextView = view.findViewById(R.id.text_view_details);
        currentTemperatureTextView = view.findViewById(R.id.text_view_current_temperature);
        updateTextView = view.findViewById(R.id.text_view_update);
    }

    public void setPlaceName(String name, String country) {
        String cityText = name.toUpperCase() + ", " + country;
        cityTextView.setText(cityText);
    }
    public void setDetails(String description, float humidity, float pressure) {
        String detailsText = description.toUpperCase() + "\n"
                + "Humidity: " + humidity + "%" + "\n"
                + "Pressure:" + pressure + "hPa";
        detailsTextView.setText(detailsText);
    }

    public void setCurrentTemp(float temp) {
        String currentTextText = String.format(Locale.getDefault(), "%.2f", temp)
                + "\u2103";
        currentTemperatureTextView.setText(currentTextText);
    }
    public void setUpdateText(long dt) {
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String updateOn = dateFormat.format(new Date(dt * 1000));
        String updatedText = "Last update: " + updateOn;
        updateTextView.setText(updatedText);
    }

    //    private void initViews() {
//        textTemperature = findViewById(R.id.text_ambient_temperature);
//        textHumidity = findViewById(R.id.text_humidity);
//    }

}
