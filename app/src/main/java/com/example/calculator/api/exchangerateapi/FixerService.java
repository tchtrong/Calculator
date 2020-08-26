package com.example.calculator.api.exchangerateapi;

import androidx.lifecycle.LiveData;

import com.example.calculator.api.response.ApiResponse;

import retrofit2.http.GET;

public interface FixerService {
    @GET("latest?access_key=2585bb5e8cdf06a9a307d3a5e09b8f26")
    LiveData<ApiResponse<ExchangeRateResponse>> getExchangeRate();
}
