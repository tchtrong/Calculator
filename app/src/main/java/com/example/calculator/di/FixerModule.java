package com.example.calculator.di;

import com.example.calculator.api.adapter.LiveDataCallAdapterFactory;
import com.example.calculator.api.exchangerateapi.FixerService;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class FixerModule {

    @NotNull
    @Contract(pure = true)
    @Singleton
    @Provides
    public static JsonSerializer<LocalDate> provideLocalDateJsonSerializer() {
        return (src, typeOfSrc, context) -> new JsonPrimitive(src.toString());
    }

    @NotNull
    @Contract(pure = true)
    @Provides
    @Singleton
    public static JsonDeserializer<LocalDate> provideLocalDateJsonDeserializer() {
        return (json, typeOfT, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString());
    }

    @NotNull
    @Singleton
    @Provides
    public static FixerService provideFixerService(
            JsonSerializer<LocalDate> localDateJsonSerializer,
            JsonDeserializer<LocalDate> localDateJsonDeserializer
    ) {
        return new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory
                        .create(
                                new GsonBuilder()
                                        .registerTypeAdapter(LocalDate.class, localDateJsonSerializer)
                                        .registerTypeAdapter(LocalDate.class, localDateJsonDeserializer)
                                        .create()))
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(FixerService.class);
    }
}
