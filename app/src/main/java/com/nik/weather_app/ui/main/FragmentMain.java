package com.nik.weather_app.ui.main;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.nik.weather_app.R;

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

    public FragmentMain() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        cityTextView = view.findViewById(R.id.text_view_city);
        detailsTextView = view.findViewById(R.id.text_view_details);
        currentTemperatureTextView = view.findViewById(R.id.text_view_current_temperature);
        updateTextView = view.findViewById(R.id.text_view_update);
        weatherIconTextView = view.findViewById(R.id.text_view_weather_icon);

        if(savedInstanceState == null) mGetDataListener.getData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mGetDataListener.getData();
        super.onViewCreated(view, savedInstanceState);

    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        if(outState == null) outState = new Bundle();
//        outState.putString("cityText", cityText);
//        outState.putString("detailsText", detailsText);
//        outState.putString("currentText", currentText);
//        outState.putString("updateText", updatedText);
//        outState.putString("icon", icon);
//        super.onSaveInstanceState(outState);
//    }

    @Override
    public void onAttach(Context context) {
        try{
            mGetDataListener = (GetDataListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement " +
                    "GetDataListener interface");
        }
        super.onAttach(context);
    }

    public void setPlaceName(String name, String country) {
        String cityText = name.toUpperCase() + ", " + country;
        if(cityTextView != null)
        cityTextView.setText(cityText);
    }

    public void setDetails(String description, float humidity, float pressure) {
        String detailsText = description.toUpperCase() + "\n"
                + "Humidity: " + humidity + "%" + "\n"
                + "Pressure:" + pressure + "hPa";
        detailsTextView.setText(detailsText);
    }

    public void setCurrentTemp(float temp) {
        String currentText = String.format(Locale.getDefault(), "%.2f", temp)
                + "\u2103";
        currentTemperatureTextView.setText(currentText);
    }

    public void setUpdateText(long dt) {
        DateFormat dateFormat = DateFormat.getTimeInstance();
        String updateOn = dateFormat.format(new Date(dt * 1000));
        String updatedText = "Last update: " + updateOn;
        updateTextView.setText(updatedText);
    }

    public void setWeatherIcon(int actualId, long sunrise, long sunset) {
        int id = actualId / 100;
        String icon = "";

        if(actualId == 800) {
            long currentTime = new Date().getTime();
            if(currentTime >= sunrise && currentTime < sunset) {
                icon = "\u2600";
                //icon = getString(R.string.weather_sunny);
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
                    icon = "\u2601";
                    // icon = getString(R.string.weather_cloudy);
                    break;
                }
            }
        }
        weatherIconTextView.setText(icon);
    }

    public interface GetDataListener {
        void getData();
    }
}
