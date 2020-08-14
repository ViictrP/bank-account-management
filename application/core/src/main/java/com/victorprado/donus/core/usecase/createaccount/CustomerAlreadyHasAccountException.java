package com.victorprado.donus.core.usecase.createaccount;

public class CustomerAlreadyHasAccountException extends RuntimeException {

    public CustomerAlreadyHasAccountException() {
        super("The customer already has a bank account");
    }
}
