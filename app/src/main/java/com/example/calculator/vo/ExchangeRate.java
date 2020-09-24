package com.example.calculator.vo;

import androidx.databinding.ObservableField;

import org.jetbrains.annotations.NotNull;

import java.util.Currency;

import lombok.Data;

@Data
public class ExchangeRate {

    @NotNull
    private final String code;

    @NotNull
    public final ObservableField<Double> value = new ObservableField<>();


    @NotNull
    private final Double rate;

    @NotNull
    private final String displayName;

    @NotNull
    private final String symbol;

    public ExchangeRate(@NotNull String code, @NotNull Double rate) {
        this.code = code;
        this.rate = rate;
        this.value.set((double) 0);
        this.displayName = Currency.getInstance(code).getDisplayName();
        this.symbol = Currency.getInstance(code).getSymbol();
    }
}
