package com.nik.weather_app;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.List;


public class FragmentSetting extends Fragment {
    private OnCitySelectedListener mOnCitySelectedListener;
    private List<String> cities;
    private Spinner spinner;
    public FragmentSetting() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinner = view.findViewById(R.id.spinner_cities);
        cities.add(0, "");
        cities.add(1, "Another city...");

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
    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
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

    }

    List<String> getCities(List<String> cities) {
        this.cities = cities;
        return cities;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCitySelectedListener {
        void citySelected(int cityID);
    }

}
