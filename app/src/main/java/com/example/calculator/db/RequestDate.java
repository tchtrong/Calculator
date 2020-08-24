package com.example.calculator.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

@Entity
@TypeConverters(value = {ExchangeRateTypeConverters.class})
public class RequestDate {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private Integer id;

    @ColumnInfo(name = "local_date")
    private LocalDate localDate;

    public RequestDate(LocalDate localDate) {
        this.id = 1;
        this.localDate = localDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

}
