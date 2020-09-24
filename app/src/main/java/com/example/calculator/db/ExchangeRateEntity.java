package com.example.calculator.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import lombok.Data;

@Data
@Entity
@TypeConverters(value = {LocalDateConverter.class})
public class ExchangeRateEntity {
    @PrimaryKey
    @NotNull
    private String code;

    @NotNull
    private Double rate;

    @NotNull
    private LocalDate date;

    public ExchangeRateEntity(@NotNull String code, @NotNull Double rate, @NotNull LocalDate date) {
        this.code = code;
        this.rate = rate;
        this.date = date;
    }
}
