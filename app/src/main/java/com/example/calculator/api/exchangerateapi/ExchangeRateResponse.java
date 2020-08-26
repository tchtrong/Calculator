package com.example.calculator.api.exchangerateapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calculator.vo.ExchangeRate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ExchangeRateResponse {
    @NonNull
    private final Boolean success;

    @Nullable
    private final Map<Integer, String> error;

    private final Instant timeStamp;

    private final String base;

    private final LocalDate date;

    private final Map<String, Double> rates;

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

    public List<ExchangeRate> asExchangeRateList() {
        ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();
        for (Map.Entry<String, Double> rate : rates.entrySet()) {
            exchangeRates.add(new ExchangeRate(rate.getKey(), rate.getValue()));
        }
        exchangeRates.add(new ExchangeRate(base, (double) 1));
        return exchangeRates;
    }
}
