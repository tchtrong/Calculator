package com.example.calculator.utils;

import androidx.lifecycle.LiveData;

/**
 * A LiveData class that has `null` value.
 */
public class AbsentLiveData<T> extends LiveData<T> {

    private AbsentLiveData() {
        postValue(null);
    }

    public LiveData<T> create() {
        return new AbsentLiveData<>();
    }

}
