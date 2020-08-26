package com.example.calculator.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.example.calculator.AppExecutors;
import com.example.calculator.api.Resource;
import com.example.calculator.api.exchangerateapi.ExchangeRateResponse;
import com.example.calculator.api.exchangerateapi.FixerService;
import com.example.calculator.api.response.ApiResponse;
import com.example.calculator.db.ExchangeRateDao;
import com.example.calculator.db.RequestDate;
import com.example.calculator.utils.NetworkBoundResource;
import com.example.calculator.utils.RateLimiter;
import com.example.calculator.vo.ExchangeRate;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExchangeRateRepository {

    private final AppExecutors appExecutors;
    private final ExchangeRateDao exchangeRateDao;
    private final FixerService fixerService;
    private final RateLimiter<String> rateLimiter;

    @Inject
    public ExchangeRateRepository(
            AppExecutors appExecutors,
            ExchangeRateDao exchangeRateDao,
            FixerService fixerService) {
        this.appExecutors = appExecutors;
        this.exchangeRateDao = exchangeRateDao;
        this.fixerService = fixerService;
        this.rateLimiter = new RateLimiter<>(1, TimeUnit.HOURS);
    }

    public LiveData<Resource<List<ExchangeRate>>> loadExchangeRateList() {
        return new NetworkBoundResource<List<ExchangeRate>, ExchangeRateResponse>(appExecutors) {

            private static final String RATE_LIMITER_KEY = "load_all";

            @Override
            protected void saveCallResult(@NonNull ExchangeRateResponse item) {
                Log.d("saveCallResult", "Number: " + item.getRates().size());
                exchangeRateDao.insert(item.asExchangeRateList());
            }

            @Override
            protected void onFetchFailed() {
                rateLimiter.reset(RATE_LIMITER_KEY);
            }

            @Override
            protected Boolean shouldFetch(@Nullable List<ExchangeRate> data) {
                //RequestDate requestDate = exchangeRateDao.loadRequestDate(1);
                return data == null || data.isEmpty() || rateLimiter.shouldFetch(RATE_LIMITER_KEY);
            }

            @Override
            protected LiveData<List<ExchangeRate>> loadFromDb() {
                return exchangeRateDao.loadExchangeRateList();
            }

            @Override
            protected LiveData<ApiResponse<ExchangeRateResponse>> createCall() {
                Log.d("createCall", " ");
                return fixerService.getExchangeRate();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ExchangeRate>> loadExchangeRate(String code) {
        return new NetworkBoundResource<ExchangeRate, ExchangeRateResponse>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull ExchangeRateResponse item) {
                exchangeRateDao.insert(item.asExchangeRateList());
            }

            @Override
            protected Boolean shouldFetch(@Nullable ExchangeRate data) {
                return data == null;
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
