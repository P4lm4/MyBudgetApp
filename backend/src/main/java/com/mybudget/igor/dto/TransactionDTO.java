package com.mybudget.igor.dto;

import com.mybudget.igor.model.Settings;
import com.mybudget.igor.model.Transaction;
import com.mybudget.igor.model.TransactionType;
import com.mybudget.igor.service.CurrencyService;

public class TransactionDTO {

    private Long id;
    private String description;
    private TransactionType type;
    private String currency;
    private Double amount;
    private Long accountId;
    private String accountName;
    private String defaultCurrency;
    private Double defaultCurrencyAmount;

    public TransactionDTO(Transaction t, Settings s, CurrencyService currencyService) {
        id = t.getId();
        description = t.getDescription();
        type = t.getType();
        currency = t.getCurrency();
        amount = t.getAmount();
        accountId = t.getAccount().getId();
        accountName = t.getAccount().getName();
        defaultCurrency = s.getCurrency();
        defaultCurrencyAmount = currencyService.getExchangeRate(currency, defaultCurrency) * amount;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }

    public String getCurrency() {
        return currency;
    }

    public Double getAmount() {
        return amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public Double getDefaultCurrencyAmount() {
        return defaultCurrencyAmount;
    }
}
