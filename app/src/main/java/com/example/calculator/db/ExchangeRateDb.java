package com.example.calculator.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.calculator.vo.ExchangeRate;

@Database(
        entities = {ExchangeRate.class, RequestDate.class},
        version = 1,
        exportSchema = false)
public abstract class ExchangeRateDb extends RoomDatabase {
    public static final String DATABASE_NAME = "exchange_rate.db";

    public abstract ExchangeRateDao exchangeRateDao();
}
