package com.example.calculator.ui.currencyconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculator.AppExecutors;
import com.example.calculator.R;
import com.example.calculator.databinding.CurrencyConverterFragmentBinding;
import com.example.calculator.utils.AutoClearedValue;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import lombok.Getter;

@AndroidEntryPoint
public class CurrencyConverterFragment extends Fragment {

    @Getter(lazy = true)
    private final CurrencyConverterViewModel currencyConverterViewModel
            = new ViewModelProvider(this).get(CurrencyConverterViewModel.class);

    @Getter(lazy = true)
    private final AutoClearedValue<CurrencyConverterFragmentBinding> binding
            = AutoClearedValue.create(this);

    @Getter(lazy = true)
    private final AutoClearedValue<ExchangeRateRecycleViewAdapter> adapter
            = AutoClearedValue.create(this);

    @Inject
    public AppExecutors appExecutors;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        CurrencyConverterFragmentBinding dataBinding
                = DataBindingUtil
                .inflate(inflater,
                        R.layout.currency_converter_fragment,
                        container,
                        false);

        getBinding().setValue(dataBinding);

        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        getBinding().getValue().setLifecycleOwner(getViewLifecycleOwner());

        getAdapter().setValue(new ExchangeRateRecycleViewAdapter(
                appExecutors,
                null,
                null
        ));

        getCurrencyConverterViewModel().getExchangeRates().observe(
                getViewLifecycleOwner(),
                listResource -> {
                    if (listResource != null) {
                        if (listResource.getData() != null) {
                            getAdapter().getValue().submitList(listResource.getData());
                        } else {
                            getAdapter().getValue().submitList(new ArrayList<>());
                        }
                    }
                }
        );

        getBinding().getValue().exchangeRateRecList.setAdapter(getAdapter().getValue());
    }
}