package com.mybudget.igor.service;

import com.mybudget.igor.model.Account;
import com.mybudget.igor.model.Transaction;
import com.mybudget.igor.repo.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public Transaction addTransaction(Transaction transaction) {
        return transactionRepo.save(transaction);
    }

    public List<Transaction> findAllTransactions() {
        return transactionRepo.findAll();
    }

    public List<Transaction> getTransactionByAccount(Account account) {
        return transactionRepo.findByAccount(account);
    }
}
