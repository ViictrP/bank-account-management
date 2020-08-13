package com.victorprado.donus.core.entity;

import com.victorprado.donus.core.constant.Number;
import com.victorprado.donus.core.usecase.createaccount.InvalidEntityException;
import com.victorprado.donus.core.validator.EntityValidator;

import java.time.LocalDateTime;
import java.util.UUID;

public class BankAccount extends Upgradable implements EntityValidator {

    private String id;
    private String number;
    private Customer customer;
    private Double balance;

    public BankAccount(Customer customer) {
        this.customer = customer;
        this.generateData();
    }

    public BankAccount(Builder builder) {
        this.customer = builder.customer;
        this.generateData();
    }

    private void generateData() {
        this.id = UUID.randomUUID().toString();
        this.number = UUID.randomUUID().toString();
        this.balance = Number.ZERO;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public void generateCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public void updateLastMofiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    @Override
    public void validate() throws InvalidEntityException {
        if (customer == null) {
            throw new InvalidEntityException();
        }
    }

    public static class Builder {
        private Customer customer;

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public BankAccount build() {
            return new BankAccount(this);
        }
    }
}
