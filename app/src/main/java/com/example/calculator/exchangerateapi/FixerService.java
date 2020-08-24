package com.example.calculator.exchangerateapi;

import androidx.lifecycle.LiveData;

import com.example.calculator.exchangerateapi.response.ApiResponse;

import retrofit2.http.GET;

public interface FixerService {
    @GET
    LiveData<ApiResponse<ExchangeRateResponse>> getExchangeRate();
}
