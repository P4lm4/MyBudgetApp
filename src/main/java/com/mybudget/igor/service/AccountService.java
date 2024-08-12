package com.mybudget.igor.service;

import com.mybudget.igor.model.Account;
import com.mybudget.igor.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepo accountRepo;

    @Autowired
    public AccountService(AccountRepo accountRepo) {this.accountRepo = accountRepo;}

    public Account addAccount(Account account) {
        return accountRepo.save(account);
    }

    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    public void deleteAccount(Long id) {
        accountRepo.deleteById(id);
    }
}
