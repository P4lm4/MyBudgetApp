package com.mybudget.igor.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String description;
    private String type;
    private Long amount;
    private Account account;

    public Payment() {}

    public Payment(String description, String type, Long amount, Account account) {
        this.description = description;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
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
        return "Account{" +
                "id= " + id +
                ", name=" + description + '\'' +
                ", currency=" + type + '\'' +
                ", balance=" + amount + '\'' +
                ", account=" + account + '\'' +
                " }";
    }
}
