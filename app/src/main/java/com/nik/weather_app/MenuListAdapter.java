package com.nik.weather_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuListAdapter extends RecyclerView.Adapter {
    private ArrayList<Integer> data;
    private Activity activity;
    private int days_count = 3;

    public MenuListAdapter(ArrayList<Integer> data, Activity activity) {
        this.activity = activity;
        if(data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<>();
        }
    }

    void setThreeDays() {
        int actual_days_count = days_count;
        days_count = 3;
        if(data.size() > 0) {
            if(days_count != actual_days_count) {
                for(int i = actual_days_count; i > days_count; i--)
                    notifyItemRemoved(i - 1);
            }
        }
    }

    void setFiveDays() {
        int actual_days_count = days_count;
        days_count = 5;
        if(data.size() > 0) {
            if(days_count != actual_days_count) {
                for(int i = actual_days_count; i < days_count; i++)
                    notifyItemInserted(i );
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout,
                parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String text = data.get(position) + "\u00B0" + "C";
        ((ViewHolder)holder).textView.setText(text);
        activity.registerForContextMenu(((ViewHolder)holder).textView);
    }

    @Override
    public int getItemCount() {
        return days_count;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_list_item);
        }
    }

}
