package com.nik.weather_app.data;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nik.weather_app.base.BaseAdapter;
import com.nik.weather_app.databinding.ForecastViewHolderBinding;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {
    private final ForecastListener listener;
    private List<ForecastViewState> list;

    public ForecastAdapter(List<ForecastViewState> forecastViewStateList, ForecastListener listener) {
        this.list = forecastViewStateList;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ForecastViewState> data) {
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ForecastViewHolderBinding binding = ForecastViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ForecastViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
