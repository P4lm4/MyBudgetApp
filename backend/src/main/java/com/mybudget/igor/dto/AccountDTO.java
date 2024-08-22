package com.mybudget.igor.dto;

import com.mybudget.igor.model.Account;
import com.mybudget.igor.model.Settings;
import com.mybudget.igor.service.CurrencyService;

public class AccountDTO {

    private Long id;
    private String name;
    private String currency;
    private Double balance;
    private String defaultCurrency;
    private Double defaultCurrencyAmount;

    public AccountDTO(Account a, Settings s, CurrencyService currencyService) {
        id = a.getId();
        name = a.getName();
        currency = a.getCurrency();
        balance = a.getBalance();
        defaultCurrency = s.getCurrency();
        defaultCurrencyAmount = currencyService.getExchangeRate(currency, defaultCurrency) * balance;

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getBalance() {
        return balance;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public Double getDefaultCurrencyAmount() {
        return defaultCurrencyAmount;
    }
}
