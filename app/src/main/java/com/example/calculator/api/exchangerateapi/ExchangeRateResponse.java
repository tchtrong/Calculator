package com.example.calculator.api.exchangerateapi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.calculator.db.ExchangeRateEntity;

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

    public List<ExchangeRateEntity> asListOfExchangeRateEntity() {
        ArrayList<ExchangeRateEntity> exchangeRates = new ArrayList<>();
        for (Map.Entry<String, Double> rate : rates.entrySet()) {
            exchangeRates.add(new ExchangeRateEntity(rate.getKey(), rate.getValue(), date));
        }
        exchangeRates.add(new ExchangeRateEntity(base, (double) 1, date));
        return exchangeRates;
    }
}
