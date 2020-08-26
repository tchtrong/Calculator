package com.example.calculator.utils;

import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.calculator.AppExecutors;
import com.example.calculator.api.Resource;
import com.example.calculator.api.response.ApiEmptyResponse;
import com.example.calculator.api.response.ApiErrorResponse;
import com.example.calculator.api.response.ApiResponse;
import com.example.calculator.api.response.ApiSuccessResponse;

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final AppExecutors appExecutors;

    private MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NetworkBoundResource(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
        result.setValue(Resource.loading(null));
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, data -> {
            result.removeSource(dbSource);
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource);
            } else {
                result.addSource(dbSource, newData -> setValue(Resource.success(newData)));
            }
        });
    }

    @MainThread
    private void setValue(Resource<ResultType> newValue) {
        if (result.getValue() != newValue) {
            result.setValue(newValue);
        }
    }

    private void fetchFromNetwork(LiveData<ResultType> dbSource) {
        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
        result.addSource(dbSource, newData -> setValue(Resource.loading(newData)));
        result.addSource(apiResponse, response -> {
            result.removeSource(apiResponse);
            result.removeSource(dbSource);
            if (response instanceof ApiSuccessResponse) {
                appExecutors.getDiskIO().execute(() -> {
                    saveCallResult(processResponse((ApiSuccessResponse<RequestType>) response));
                    appExecutors.getMainThread().execute(() ->
                            result.addSource(loadFromDb(), newData
                                    -> setValue(Resource.success(newData))));
                });
            } else if (response instanceof ApiEmptyResponse) {
                appExecutors.getMainThread().execute(() ->
                        result.addSource(loadFromDb(), newData
                                -> setValue(Resource.success(newData))));
            } else if (response instanceof ApiErrorResponse) {
                onFetchFailed();
                result.addSource(dbSource, newData ->
                        setValue(Resource.error(
                                ((ApiErrorResponse<RequestType>) response).getError(), newData)));
            }
        });
    }

    protected void onFetchFailed() {
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    @WorkerThread
    protected RequestType processResponse(@NonNull ApiSuccessResponse<RequestType> response) {
        return response.getBody();
    }

    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    @MainThread
    protected abstract Boolean shouldFetch(@Nullable ResultType data);

    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @MainThread
    protected abstract LiveData<ApiResponse<RequestType>> createCall();

}
