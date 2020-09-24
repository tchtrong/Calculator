package com.example.calculator.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ExchangeRateEntity> exchangeRates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RequestDate requestDate);

    @Query("SELECT * FROM requestdate WHERE id = :id")
    RequestDate loadRequestDate(Integer id);

    @Query(value = "SELECT * FROM ExchangeRateEntity")
    LiveData<List<ExchangeRateEntity>> loadExchangeRateList();

    @Query(value = "SELECT * FROM ExchangeRateEntity WHERE code = :code")
    LiveData<ExchangeRateEntity> loadExchangeRate(String code);

}
