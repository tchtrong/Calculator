package com.example.calculator.ui.currencyconverter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calculator.AppExecutors;
import com.example.calculator.R;
import com.example.calculator.databinding.CurrencyConverterFragmentBinding;
import com.example.calculator.utils.AutoClearedValue;
import com.example.calculator.utils.Calculator;

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
                        getAdapter().getValue().submitList(listResource);
                    } else {
                        getAdapter().getValue().submitList(new ArrayList<>());
                    }
                }
        );

        getBinding().getValue().exchangeRateRecList.setAdapter(getAdapter().getValue());

        setUpInputAndKeyboard();
    }

    private void setUpInputAndKeyboard() {
        EditText inputFormula = getBinding().getValue().inputComponents.findViewById(R.id.txt_input_formula);

        inputFormula.setShowSoftInputOnFocus(false);
        inputFormula.setTextIsSelectable(true);

        final InputConnection inputConnection = inputFormula.onCreateInputConnection(new EditorInfo());

        ConstraintLayout inputComponents = getBinding().getValue().inputComponents;
        for (int i = 0; i < inputComponents.getChildCount(); ++i) {
            View v = inputComponents.getChildAt(i);
            if (v instanceof Button) {
                v.setOnClickListener(view -> {
                    if (inputConnection != null) {
                        int id = view.getId();
                        if (id == R.id.btn_del) {
                            CharSequence selectedText = inputConnection.getSelectedText(0);
                            if (TextUtils.isEmpty(selectedText)) {
                                inputConnection.deleteSurroundingText(1, 0);
                            } else {
                                inputConnection.commitText("", 1);
                            }
                        } else if (id == R.id.btn_eq) {
                            if (!inputFormula.getText().toString().equals("")) {
                                try {
                                    double result = Calculator.eval(inputFormula.getText().toString());
                                    String res = Double.valueOf(result).toString();
                                    inputFormula.setText(res);
                                    inputFormula.selectAll();
                                    getCurrencyConverterViewModel().updateMoney(result);
                                } catch (RuntimeException e) {
                                    String bad = "BAD EXPRESSION!!!!!";
                                    inputFormula.setText(bad);
                                    inputFormula.selectAll();
                                }
                            } else {
                                getCurrencyConverterViewModel().updateMoney((double) 0);
                            }
                        } else {
                            inputConnection.commitText(((Button) view).getText(), 1);
                        }
                    }
                });
            }
        }
    }
}