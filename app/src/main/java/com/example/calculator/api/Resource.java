package com.example.calculator.api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import lombok.Data;

@Data
public class Resource<T> {
    @NonNull
    private final Status status;

    @Nullable
    private final T data;

    @Nullable
    private final String message;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <R> Resource<R> success(@Nullable R data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <R> Resource<R> error(@NonNull String message, @Nullable R data) {
        return new Resource<>(Status.ERROR, data, message);
    }

    public static <R> Resource<R> loading(@Nullable R data) {
        return new Resource<>(Status.LOADING, data, null);
    }
}
