package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.exception.CoreException;

public class CustomerAlreadyHasAccountException extends CoreException {

    public CustomerAlreadyHasAccountException() {
        super("The customer already has a bank account");
    }
}
