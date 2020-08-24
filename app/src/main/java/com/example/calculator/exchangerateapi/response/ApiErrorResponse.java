package com.example.calculator.exchangerateapi.response;

public final class ApiErrorResponse<T> extends ApiResponse<T> {
    private String error;

    public ApiErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error == null ? "Unknown error" : error;
    }
}
