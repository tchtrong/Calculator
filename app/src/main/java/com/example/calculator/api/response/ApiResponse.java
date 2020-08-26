package com.example.calculator.api.response;

import com.example.calculator.api.exchangerateapi.ExchangeRateResponse;

import java.io.IOException;
import java.util.Map;

import retrofit2.Response;

public abstract class ApiResponse<T> {

    public static <R> ApiErrorResponse<R> create(Throwable throwable) {
        return new ApiErrorResponse<>(
                throwable.getMessage() != null ? throwable.getMessage() : "Unknown error");
    }

    public static <R> ApiResponse<R> create(Response<R> response) throws IOException {
        if (response.isSuccessful()) {
            R body = response.body();
            if (body == null || response.code() == 204) {
                return new ApiEmptyResponse<>();
            } else {
                if (body instanceof ExchangeRateResponse) {
                    Map<Integer, String> errors = ((ExchangeRateResponse) body).getError();
                    if (errors != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (Map.Entry<Integer, String> msg : errors.entrySet()) {
                            stringBuilder.append(msg.getKey()).append(": ").append(msg.getValue()).append("\n");
                        }
                        return new ApiErrorResponse<>(stringBuilder.toString());
                    }
                }
                return new ApiSuccessResponse<>(body);
            }
        } else {
            String msg = response.errorBody() == null ? null : response.errorBody().string();
            String errorMsg =
                    msg == null || msg.isEmpty()
                            ? response.message()
                            : msg;
            return new ApiErrorResponse<>(errorMsg);
        }
    }
}

