package com.example.calculator.utils;

import android.os.SystemClock;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import kotlin.jvm.Synchronized;
import lombok.val;

public class RateLimiter<KEY> {
    private final Map<KEY, Long> timestamps = new HashMap<>();
    private final long timeout;

    public RateLimiter(@NotNull Integer timeout, @NotNull TimeUnit timeUnit) {
        this.timeout = timeUnit.toMillis(timeout.longValue());
    }

    @Synchronized
    public Boolean shouldFetch(KEY key) {
        val lastFetched = timestamps.get(key);
        val now = now();
        if (lastFetched == null) {
            timestamps.put(key, now);
            return true;
        }
        if (now - lastFetched > timeout) {
            timestamps.put(key, now);
            return true;
        }
        return false;
    }

    private long now() {
        return SystemClock.uptimeMillis();
    }

    @Synchronized
    public void reset(KEY key) {
        timestamps.remove(key);
    }
}
