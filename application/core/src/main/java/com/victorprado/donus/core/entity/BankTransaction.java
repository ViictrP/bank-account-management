package com.victorprado.donus.core.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class BankTransaction {

    private String id;
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;
    private TransactionType type;
    private BigDecimal value;
    private LocalDateTime when;

    public BankTransaction() {
        this.id = UUID.randomUUID().toString();
    }

    public BankTransaction(Builder builder) {
        this.id = UUID.randomUUID().toString();
        this.sourceAccount = builder.sourceAccount;
        this.destinationAccount = builder.destinationAccount;
        this.type = builder.type;
        this.value = builder.value;
        this.when = builder.when;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(BankAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public BankAccount getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(BankAccount destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getWhen() {
        return when;
    }

    public void setWhen(LocalDateTime when) {
        this.when = when;
    }

    public static class Builder {
        private BankAccount sourceAccount;
        private BankAccount destinationAccount;
        private TransactionType type;
        private BigDecimal value;
        private LocalDateTime when;

        public Builder sourceAccount(BankAccount sourceAccount) {
            this.sourceAccount = sourceAccount;
            return this;
        }
        public Builder destinationAccount(BankAccount destinationAccount) {
            this.destinationAccount = destinationAccount;
            return this;
        }
        public Builder type(TransactionType type) {
            this.type = type;
            return this;
        }
        public Builder value(BigDecimal value) {
            this.value = value;
            return this;
        }
        public Builder when(LocalDateTime when) {
            this.when = when;
            return this;
        }

        public BankTransaction build() {
            return new BankTransaction(this);
        }
    }
}
