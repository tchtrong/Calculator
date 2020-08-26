package com.example.calculator.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.calculator.AppExecutors;
import com.example.calculator.db.ExchangeRateDao;
import com.example.calculator.db.RequestDate;
import com.example.calculator.exchangerateapi.ExchangeRateResponse;
import com.example.calculator.exchangerateapi.FixerService;
import com.example.calculator.exchangerateapi.Resource;
import com.example.calculator.exchangerateapi.response.ApiResponse;
import com.example.calculator.utils.NetworkBoundResource;
import com.example.calculator.vo.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExchangeRateRepository {
    private AppExecutors appExecutors;
    private ExchangeRateDao exchangeRateDao;
    private FixerService fixerService;

    @Inject
    public ExchangeRateRepository(
            AppExecutors appExecutors,
            ExchangeRateDao exchangeRateDao,
            FixerService fixerService) {
        this.appExecutors = appExecutors;
        this.exchangeRateDao = exchangeRateDao;
        this.fixerService = fixerService;
    }

    public LiveData<Resource<List<ExchangeRate>>> loadExchangeRateList() {
        return new NetworkBoundResource<List<ExchangeRate>, ExchangeRateResponse>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull ExchangeRateResponse item) {
                exchangeRateDao.insert(item.asExchangeRateList());
                exchangeRateDao.insert(new RequestDate(item.getDate()));
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<ExchangeRate> data) {
                return data == null || data.isEmpty()
                        || exchangeRateDao
                        .loadRequestDate(1).getLocalDate().compareTo(LocalDate.now()) >= 1;
            }

            @Override
            protected LiveData<List<ExchangeRate>> loadFromDb() {
                return exchangeRateDao.loadExchangeRateList();
            }

            @Override
            protected LiveData<ApiResponse<ExchangeRateResponse>> createCall() {
                return fixerService.getExchangeRate();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ExchangeRate>> loadExchangeRate(String code) {
        return new NetworkBoundResource<ExchangeRate, ExchangeRateResponse>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull ExchangeRateResponse item) {
                exchangeRateDao.insert(item.asExchangeRateList());
                exchangeRateDao.insert(new RequestDate(item.getDate()));
            }

            @Override
            protected Boolean shouldFetch(@Nullable ExchangeRate data) {
                return data == null
                        || exchangeRateDao
                        .loadRequestDate(1).getLocalDate().compareTo(LocalDate.now()) >= 1;
            }

            @Override
            protected LiveData<ExchangeRate> loadFromDb() {
                return exchangeRateDao.loadExchangeRate(code);
            }

            @Override
            protected LiveData<ApiResponse<ExchangeRateResponse>> createCall() {
                return fixerService.getExchangeRate();
            }
        }.asLiveData();
    }
}
