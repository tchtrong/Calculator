package com.example.calculator.exchangerateapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calculator.vo.ExchangeRate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExchangeRateResponse {
    @NonNull
    private Boolean success;

    @Nullable
    private Map<Integer, String> error;

    private Instant timeStamp;

    private String base;

    private LocalDate date;

    private Map<String, Double> rates;

    public ExchangeRateResponse(
            @NonNull Boolean success,
            @Nullable Map<Integer, String> error, Instant timeStamp, String base,
            LocalDate date, Map<String, Double> rates) {
        this.success = success;
        this.error = error;
        this.timeStamp = timeStamp;
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    @Nullable
    public Map<Integer, String> getError() {
        return error;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<ExchangeRate> asExchangeRateList() {
        ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
        for (Map.Entry<String, Double> rate : rates.entrySet()) {
            exchangeRates.add(new ExchangeRate(rate.getKey(), rate.getValue()));
        }
        return exchangeRates;
    }
}
