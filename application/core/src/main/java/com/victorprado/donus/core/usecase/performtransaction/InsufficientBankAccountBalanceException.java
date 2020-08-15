package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.exception.CoreException;

public class InsufficientBankAccountBalanceException extends CoreException {

    public InsufficientBankAccountBalanceException(String accountNumber) {
        super("Account " + accountNumber + " does not have enough balance to perform this transaction");
    }
}
