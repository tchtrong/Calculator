package com.example.calculator.ui.currencyconverter;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.calculator.api.Resource;
import com.example.calculator.db.ExchangeRateEntity;
import com.example.calculator.repository.ExchangeRateRepository;
import com.example.calculator.utils.EntityToViewMapper;
import com.example.calculator.vo.ExchangeRate;

import java.util.List;
import java.util.Objects;

public class CurrencyConverterViewModel extends ViewModel {

    private final LiveData<Resource<List<ExchangeRateEntity>>> exchangeRatesEntities;

    private final Observer<Resource<List<ExchangeRateEntity>>> exchangeRateEntityObserver;

    private final MutableLiveData<List<ExchangeRate>> exchangeRates;

    @ViewModelInject
    public CurrencyConverterViewModel(ExchangeRateRepository exchangeRateRepository,
                                      @Assisted SavedStateHandle savedStateHandle) {
        this.exchangeRatesEntities = exchangeRateRepository.loadExchangeRateList();
        this.exchangeRates = new MutableLiveData<>();
        this.exchangeRateEntityObserver = listResource -> {
            if (listResource != null) {
                exchangeRates.postValue(EntityToViewMapper.fromList(listResource.getData()));
            }
        };
        this.exchangeRatesEntities.observeForever(exchangeRateEntityObserver);
    }

    public LiveData<List<ExchangeRate>> getExchangeRates() {
        return exchangeRates;
    }

    void updateMoney(Double newValue) {
        for (ExchangeRate exchangeRate : Objects.requireNonNull(exchangeRates.getValue())) {
            exchangeRate.getValue().set(newValue);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        exchangeRatesEntities.observeForever(exchangeRateEntityObserver);
    }
}