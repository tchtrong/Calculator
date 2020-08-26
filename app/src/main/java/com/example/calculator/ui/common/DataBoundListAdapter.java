package com.example.calculator.ui.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.calculator.AppExecutors;

public abstract class DataBoundListAdapter<T, V extends ViewDataBinding>
        extends ListAdapter<T, DataBoundViewHolder<V>> {

    public DataBoundListAdapter(@NonNull AppExecutors appExecutors, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(new AsyncDifferConfig.Builder<>(diffCallback)
                .setBackgroundThreadExecutor(appExecutors.getDiskIO())
                .build());
    }

    @NonNull
    @Override
    public DataBoundViewHolder<V> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataBoundViewHolder<>(createBinding(parent));
    }

    protected abstract V createBinding(ViewGroup parent);

    @Override
    public void onBindViewHolder(@NonNull DataBoundViewHolder<V> holder, int position) {
        bind(holder.getBinding(), getItem(position));
        holder.getBinding().executePendingBindings();
    }

    protected abstract void bind(V binding, T item);
}
