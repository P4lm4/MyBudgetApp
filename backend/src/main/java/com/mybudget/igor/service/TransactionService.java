package com.mybudget.igor.service;

import com.mybudget.igor.dto.TransactionDTO;
import com.mybudget.igor.model.Account;
import com.mybudget.igor.model.Settings;
import com.mybudget.igor.model.Transaction;
import com.mybudget.igor.model.TransactionType;
import com.mybudget.igor.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final CurrencyService currencyService;
    private final SettingsService settingsService;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo, CurrencyService currencyService, SettingsService settingsService) {
        this.transactionRepo = transactionRepo;
        this.currencyService = currencyService;
        this.settingsService = settingsService;
    }

    public Transaction addTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        Double accBalance = account.getBalance();

        if(transaction.getDescription().length() > 30 || transaction.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Description length must be less than or equal to 30 characters and can not be an empty string.");
        }
        if(!currencyService.getAllCurrencies().containsKey(transaction.getCurrency())) {
            throw  new IllegalArgumentException("Currency " + transaction.getCurrency() + " does not exist.");
        }

        if((transaction.getType() == TransactionType.EXPENSE || transaction.getType() == TransactionType.INCOME) && transaction.getAmount() > 0) {
            Double amount = transaction.getAmount();

            // Make the expense negative so we can always just add the numbers
            if(transaction.getType() == TransactionType.EXPENSE) {
                amount *= -1;
            }
            // If the currencies are not the same, do the conversion
            if(!Objects.equals(transaction.getCurrency(), account.getCurrency())) {
                amount = amount * currencyService.getExchangeRate(transaction.getCurrency(), account.getCurrency());
            }
            if(accBalance + amount < 0) {
                throw new IllegalArgumentException("You don't have enough in your account, current balance: " + accBalance);
            }
            account.setBalance(accBalance + amount);

            return transactionRepo.save(transaction);
        } else {
            throw new IllegalArgumentException("Unsupported transaction.");
        }

    }

    public List<Transaction> findAllTransactions() {
        return transactionRepo.findAll();
    }

    public List<Transaction> getTransactionByAccount(Account account) {
        return transactionRepo.findByAccount(account);
    }

    public List<TransactionDTO> convertToDTOs(List<Transaction> list) {
        ArrayList<TransactionDTO> dtos = new ArrayList<>();
        Settings settings = settingsService.getSettings();
        for(Transaction t: list) {
            dtos.add(new TransactionDTO(t, settings, currencyService));
        }
        return dtos;
    }

    public void deleteAll() {transactionRepo.deleteAll();}
}
