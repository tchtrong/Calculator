package com.example.calculator.exchangerateapi.adapter;

import androidx.lifecycle.LiveData;

import com.example.calculator.exchangerateapi.response.ApiResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.internal.EverythingIsNonNull;

public class LiveDataCallAdapterFactory extends CallAdapter.Factory {
    @EverythingIsNonNull
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = getParameterUpperBound(0, (ParameterizedType) returnType);
        Type rawObservableType = getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("Type must be a resource");
        }
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType);
    }
}
