package com.example.calculator.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import lombok.Data;

@Data
public class AutoClearedValue<T> {

    private T value;

    public AutoClearedValue(@NotNull Fragment fragment) {
        fragment.getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onCreate(@NonNull LifecycleOwner owner) {
                fragment.getViewLifecycleOwnerLiveData().observe(fragment, lifecycleOwner -> {
                    if (lifecycleOwner != null) {
                        lifecycleOwner.getLifecycle().addObserver(new DefaultLifecycleObserver() {
                            @Override
                            public void onDestroy(@NonNull LifecycleOwner owner) {
                                value = null;
                            }
                        });
                    }
                });
            }
        });
    }

    @NotNull
    @Contract("_ -> new")
    public static <R> AutoClearedValue<R> create(Fragment fragment) {
        return new AutoClearedValue<>(fragment);
    }
}
