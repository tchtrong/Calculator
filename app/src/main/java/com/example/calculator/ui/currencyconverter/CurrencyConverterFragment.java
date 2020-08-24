package com.example.calculator.ui.currencyconverter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.calculator.R;

public class CurrencyConverterFragment extends Fragment {

    private CurrencyConverterViewModel mViewModel;

    public static CurrencyConverterFragment newInstance() {
        return new CurrencyConverterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.currency_converter_fragment, container, false);
    }

}