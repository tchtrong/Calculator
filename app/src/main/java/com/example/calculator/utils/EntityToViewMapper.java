package com.example.calculator.utils;

import com.example.calculator.db.ExchangeRateEntity;
import com.example.calculator.vo.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EntityToViewMapper {

    @Inject
    public EntityToViewMapper() {
    }

    public static List<ExchangeRate> fromList(List<ExchangeRateEntity> exchangeRateEntities) {
        if (exchangeRateEntities == null) {
            return null;
        } else {
            List<ExchangeRate> exchangeRates = new ArrayList<>();
            for (ExchangeRateEntity entity : exchangeRateEntities) {
                exchangeRates.add(fromObject(entity));
            }
            return exchangeRates;
        }
    }

    public static ExchangeRate fromObject(ExchangeRateEntity exchangeRateEntity) {
        if (exchangeRateEntity == null) {
            return null;
        } else {
            return new ExchangeRate(exchangeRateEntity.getCode(), exchangeRateEntity.getRate());
        }
    }
}
