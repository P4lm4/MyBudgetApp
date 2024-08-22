package com.mybudget.igor.repo;

import com.mybudget.igor.model.Account;
import com.mybudget.igor.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    List<Transaction> findByAccount(Account account);
    void deleteAll();
}
