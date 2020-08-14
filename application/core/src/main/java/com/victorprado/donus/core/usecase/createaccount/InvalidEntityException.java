package com.victorprado.donus.core.usecase.createaccount;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException() {
        super("Some required fields is missing.");
    }
}
