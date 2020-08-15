package com.victorprado.donus.core.usecase.performtransaction;

public class InsufficientBankAccountBalanceException extends RuntimeException {

    public InsufficientBankAccountBalanceException(String accountNumber) {
        super("Account " + accountNumber + " does not have enough balance to perform this transaction");
    }
}
