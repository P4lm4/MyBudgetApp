package com.mybudget.igor.repo;

import com.mybudget.igor.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
