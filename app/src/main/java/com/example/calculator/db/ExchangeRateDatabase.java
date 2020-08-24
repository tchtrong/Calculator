package com.example.calculator.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.calculator.vo.ExchangeRate;

@Database(entities = {ExchangeRate.class, RequestDate.class},
        version = 1,
        exportSchema = false)
public abstract class ExchangeRateDatabase extends RoomDatabase {
    abstract ExchangeRateDao exchangeRateDao();
}
