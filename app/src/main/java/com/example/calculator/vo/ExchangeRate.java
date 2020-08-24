package com.example.calculator.vo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity
public class ExchangeRate {
    @PrimaryKey
    @NotNull
    private String code;

    @NotNull
    private Double rate;

    public ExchangeRate(@NotNull String code, @NotNull Double rate) {
        this.code = code;
        this.rate = rate;
    }

    @NotNull
    public String getCode() {
        return code;
    }

    public void setCode(@NotNull String code) {
        this.code = code;
    }

    @NotNull
    public Double getRate() {
        return rate;
    }

    public void setRate(@NotNull Double rate) {
        this.rate = rate;
    }
}
