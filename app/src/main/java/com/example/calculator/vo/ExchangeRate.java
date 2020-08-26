package com.example.calculator.vo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Currency;

import lombok.Data;

@Data
@Entity
public class ExchangeRate {
    @PrimaryKey
    @NotNull
    private final String code;

    @NotNull
    private final Double rate;

    @Ignore
    private final String displayName;

    @Ignore
    private final String symbol;

    public ExchangeRate(@NotNull String code, @NotNull Double rate) {
        this.code = code;
        this.rate = rate;
        Currency currency = Currency.getInstance(code);
        displayName = currency.getDisplayName();
        symbol = currency.getSymbol();
    }
}
