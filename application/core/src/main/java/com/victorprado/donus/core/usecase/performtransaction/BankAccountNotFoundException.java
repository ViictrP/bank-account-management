package com.victorprado.donus.core.usecase.performtransaction;

public class BankAccountNotFoundException extends RuntimeException {

    public BankAccountNotFoundException() {
        super("The bank account was not found.");
    }
}
