package com.example.calculator.exchangerateapi.response;

public final class ApiSuccessResponse<T> extends ApiResponse<T> {
    private T body;

    public ApiSuccessResponse(T body) {
        this.body = body;
    }

    public T getBody() {
        return body;
    }
}
