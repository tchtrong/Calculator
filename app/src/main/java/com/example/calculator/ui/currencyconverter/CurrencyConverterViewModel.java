package com.example.calculator.ui.currencyconverter;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.calculator.api.Resource;
import com.example.calculator.repository.ExchangeRateRepository;
import com.example.calculator.vo.ExchangeRate;

import java.util.List;

import lombok.Getter;

public class CurrencyConverterViewModel extends ViewModel {

    @Getter
    private final ExchangeRateRepository exchangeRateRepository;

    @Getter
    private final SavedStateHandle savedStateHandle;

    @ViewModelInject
    public CurrencyConverterViewModel(ExchangeRateRepository exchangeRateRepository,
                                      @Assisted SavedStateHandle savedStateHandle) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.savedStateHandle = savedStateHandle;
    }

    public LiveData<Resource<List<ExchangeRate>>> getExchangeRates() {
        return exchangeRateRepository.loadExchangeRateList();
    }

}