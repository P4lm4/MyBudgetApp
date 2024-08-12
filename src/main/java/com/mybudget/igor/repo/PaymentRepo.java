package com.mybudget.igor.repo;

import com.mybudget.igor.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
