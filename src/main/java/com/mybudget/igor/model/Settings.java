package com.mybudget.igor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Date;
@Entity
public class Settings implements Serializable {
    @Id
    @JsonIgnore
    Long id = 1L;
    String currency;
    Date date;

    public Settings () {}
    public Settings(String currency, Date date) {
        this.currency = currency;
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
