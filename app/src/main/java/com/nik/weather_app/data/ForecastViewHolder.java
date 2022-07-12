package com.nik.weather_app.data;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nik.weather_app.databinding.ForecastViewHolderBinding;

public class ForecastViewHolder extends RecyclerView.ViewHolder {
    private final ForecastViewHolderBinding bindingView;
    public ForecastViewHolder(@NonNull ForecastViewHolderBinding bindingView) {
        super(bindingView.getRoot());
        this.bindingView = bindingView;
    }

    public void bind(ForecastViewState forecastViewState, ForecastListener listener) {
        bindingView.setForecastViewState(forecastViewState);
        bindingView.setListener(listener);
    }
}
