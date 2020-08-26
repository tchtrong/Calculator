package com.example.calculator.api.response;

public final class ApiErrorResponse<T> extends ApiResponse<T> {
    private final String error;

    public ApiErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error == null ? "Unknown error" : error;
    }
}
