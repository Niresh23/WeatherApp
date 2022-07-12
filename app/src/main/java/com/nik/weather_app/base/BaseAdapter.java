package com.nik.weather_app.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.nik.weather_app.data.BaseViewHolder;
import com.nik.weather_app.data.ListAdapterItem;

import java.util.List;

public abstract class BaseAdapter<B extends ViewDataBinding, T extends ListAdapterItem> extends RecyclerView.Adapter<BaseViewHolder<B>> {
    @BindingAdapter("setAdapter")
    public static void setAdapter(RecyclerView recyclerView,
                                  BaseAdapter<ViewDataBinding, ListAdapterItem> adapter) {
        recyclerView.setAdapter(adapter);
    }

    @LayoutRes
    private final int layoutId;

    protected abstract void bind(B binding, T item);

    private List<T> data;

    public BaseAdapter(int layoutId) {
        this.layoutId = layoutId;
    }

    public void updateData(List<T> list) {
        int startChangeIndex = Math.max((data.size() - 1), 0);
        int itemsCount = list.size();
        this.data.addAll(list);
        notifyItemChanged(startChangeIndex, itemsCount);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public BaseViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        B binder = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                layoutId,
                parent,
                false
        );
        return new BaseViewHolder<>(binder);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<B> holder, int position) {
        bind(holder.getBinder(), data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
