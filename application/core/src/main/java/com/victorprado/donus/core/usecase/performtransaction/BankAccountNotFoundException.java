package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.exception.CoreException;

public class BankAccountNotFoundException extends CoreException {

    public BankAccountNotFoundException() {
        super("The bank account was not found.");
    }
}
