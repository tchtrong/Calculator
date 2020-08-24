package com.example.calculator.db;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.time.LocalDate;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExchangeRateTypeConverters {
    @Inject
    public ExchangeRateTypeConverters() {
    }

    @TypeConverter
    @Nullable
    public static String localDateToString(@Nullable LocalDate data) {
        if (data == null) {
            return null;
        } else {
            return data.toString();
        }
    }

    @TypeConverter
    @Nullable
    public static LocalDate stringToLocalDate(@Nullable String data) {
        if (data == null) {
            return null;
        } else {
            return LocalDate.parse(data);
        }
    }
}
