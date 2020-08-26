package com.example.calculator.di;

import android.content.Context;

import androidx.room.Room;

import com.example.calculator.db.ExchangeRateDao;
import com.example.calculator.db.ExchangeRateDb;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {

    @NotNull
    @Singleton
    @Provides
    public static ExchangeRateDb provideDb(@ApplicationContext Context context) {
        return Room
                .databaseBuilder(context, ExchangeRateDb.class, ExchangeRateDb.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    public static ExchangeRateDao provideExchangeRateDao(@NotNull ExchangeRateDb exchangeRateDb) {
        return exchangeRateDb.exchangeRateDao();
    }
}
