package com.example.calculator.api.response;

public final class ApiSuccessResponse<T> extends ApiResponse<T> {
    private final T body;

    public ApiSuccessResponse(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
