package com.example.calculator.ui.currencyconverter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;

import com.example.calculator.AppExecutors;
import com.example.calculator.R;
import com.example.calculator.databinding.ExchangeRateItemsBinding;
import com.example.calculator.ui.common.DataBoundListAdapter;
import com.example.calculator.vo.ExchangeRate;

public class ExchangeRateRecycleViewAdapter
        extends DataBoundListAdapter<ExchangeRate, ExchangeRateItemsBinding> {

    @Nullable
    private DataBindingComponent dataBindingComponent;

    @Nullable
    private ExchangeRateClickCallback exchangeRateClickCallback;

    public ExchangeRateRecycleViewAdapter(
            @NonNull AppExecutors appExecutors,
            @Nullable DataBindingComponent dataBindingComponent,
            @Nullable ExchangeRateClickCallback exchangeRateClickCallback) {
        super(appExecutors, new DiffUtil.ItemCallback<ExchangeRate>() {
            @Override
            public boolean areItemsTheSame(@NonNull ExchangeRate oldItem, @NonNull ExchangeRate newItem) {
                return oldItem.getCode().equals(newItem.getCode());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ExchangeRate oldItem, @NonNull ExchangeRate newItem) {
                return oldItem.getRate().equals(newItem.getRate());
            }
        });
        this.dataBindingComponent = dataBindingComponent;
        this.exchangeRateClickCallback = exchangeRateClickCallback;
    }

    @Override
    protected ExchangeRateItemsBinding createBinding(ViewGroup parent) {
        ExchangeRateItemsBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.exchange_rate_items,
                        parent,
                        false,
                        dataBindingComponent);

        binding.getRoot().setOnClickListener(v -> {
            if (binding.getExchangeRate() != null) {
                if (exchangeRateClickCallback != null) {
                    exchangeRateClickCallback.onClick(binding.getExchangeRate());
                }
            }
        });

        return binding;
    }

    @Override
    protected void bind(ExchangeRateItemsBinding binding, ExchangeRate item) {
        binding.setExchangeRate(item);
    }
}

