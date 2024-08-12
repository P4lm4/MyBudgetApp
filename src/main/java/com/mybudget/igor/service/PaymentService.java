package com.mybudget.igor.service;

import com.mybudget.igor.model.Payment;
import com.mybudget.igor.repo.PaymentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepo paymentRepo;

    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public Payment addPayment(Payment payment) {
        return paymentRepo.save(payment);
    }

    public List<Payment> findAllPayments() {
        return paymentRepo.findAll();
    }
}
