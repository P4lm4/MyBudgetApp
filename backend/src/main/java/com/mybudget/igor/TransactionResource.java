package com.mybudget.igor;

import com.mybudget.igor.model.Account;
import com.mybudget.igor.model.Transaction;
import com.mybudget.igor.service.AccountService;
import com.mybudget.igor.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionResource {
    private final TransactionService transactionService;
    private final AccountService accountService;

    public TransactionResource(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.findAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/add/{accountId}")
    public ResponseEntity<Transaction> addTransaction(@PathVariable Long accountId, @RequestBody Transaction transaction) {
        Account account = accountService.getAccountById(accountId);
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        transaction.setAccount(account);
        Transaction newTransaction = transactionService.addTransaction(transaction);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        if(account == null) {
            return new  ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Transaction> transaction = transactionService.getTransactionByAccount(account);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
