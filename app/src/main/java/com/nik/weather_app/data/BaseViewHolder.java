package com.nik.weather_app.data;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final B binder;

    public BaseViewHolder(@NonNull B binder) {
        super(binder.getRoot());
        this.binder = binder;
    }

    public B getBinder() {
        return binder;
    }
}
