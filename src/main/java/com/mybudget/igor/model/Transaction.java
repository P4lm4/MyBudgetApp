package com.mybudget.igor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name="transactionType")
    private TransactionType type;
    private String currency;
    private Double amount;
    @ManyToOne
    @JoinColumn(name="account_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Account account;

    public Transaction() {}

    public Transaction(String description, TransactionType type, String currency, Double amount, Account account) {
        this.description = description;
        this.type = type;
        this.currency = currency;
        this.amount = amount;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id= " + id +
                ", name=" + description + '\'' +
                ", currency=" + type + '\'' +
                ", balance=" + amount + '\'' +
                ", account=" + account + '\'' +
                " }";
    }
}
