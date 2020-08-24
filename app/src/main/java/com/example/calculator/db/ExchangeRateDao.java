package com.example.calculator.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.calculator.vo.ExchangeRate;

import java.util.List;

@Dao
public interface ExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ExchangeRate> exchangeRates);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RequestDate requestDate);

    @Query("SELECT * FROM requestdate WHERE id = :id")
    RequestDate loadRequestDate(Integer id);

    @Query(value = "SELECT * FROM exchangerate")
    LiveData<List<ExchangeRate>> loadExchangeRateList();

    @Query(value = "SELECT * FROM exchangerate WHERE code = :code")
    LiveData<ExchangeRate> loadExchangeRate(String code);

}
