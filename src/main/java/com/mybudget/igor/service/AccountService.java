package com.mybudget.igor.service;

import com.mybudget.igor.model.Account;
import com.mybudget.igor.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepo accountRepo;
    private final CurrencyService currencyService;

    @Autowired
    public AccountService(AccountRepo accountRepo, CurrencyService currencyService) {this.accountRepo = accountRepo;
        this.currencyService = currencyService;
    }

    public Account addAccount(Account account) {
        if(account.getName().length() > 20 || account.getName().isEmpty()) {
            throw new IllegalArgumentException("Name length must be less than or equal to 20 characters and can not be an empty string.");
        }
        if(!currencyService.getAllCurrencies().containsKey(account.getCurrency())) {
            throw  new IllegalArgumentException("Currency " + account.getCurrency() + " does not exist.");
        }

        return accountRepo.save(account);
    }
    public Account getAccountById(Long id) {
        return accountRepo.findById(id).orElse(null);
    }

    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    public void deleteAccount(Long id) {
        accountRepo.deleteById(id);
    }
}
