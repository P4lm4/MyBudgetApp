package com.mybudget.igor.service;

import com.mybudget.igor.dto.AccountDTO;
import com.mybudget.igor.model.Account;
import com.mybudget.igor.model.Settings;
import com.mybudget.igor.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    private final AccountRepo accountRepo;
    private final CurrencyService currencyService;
    private final SettingsService settingsService;

    @Autowired
    public AccountService(AccountRepo accountRepo, CurrencyService currencyService, SettingsService settingsService) {
        this.accountRepo = accountRepo;
        this.currencyService = currencyService;
        this.settingsService = settingsService;
    }

    public Account addAccount(Account account) {
        if(account.getName().length() > 20 || account.getName().isEmpty()) {
            throw new IllegalArgumentException("Name length must be less than or equal to 20 characters and can not be an empty string.");
        }
        if(!currencyService.getAllCurrencies().containsKey(account.getCurrency())) {
            throw  new IllegalArgumentException("Currency " + account.getCurrency() + " does not exist.");
        }

        if(account.getBalance() < 0) {
            throw new IllegalArgumentException("The balance cannot be below zero. ");
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

    public List<AccountDTO> convertToDTOs(List<Account> list) {
        ArrayList<AccountDTO> dtos = new ArrayList<>();
        Settings settings = settingsService.getSettings();
        for(Account a: list) {
            dtos.add(new AccountDTO(a, settings, currencyService));
        }
        return dtos;
    }

    public void deleteAll() {accountRepo.deleteAll();}
}
