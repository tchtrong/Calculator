package com.example.calculator.db;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;

import java.time.LocalDate;

public class LocalDateConverter {

    public LocalDateConverter() {
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
