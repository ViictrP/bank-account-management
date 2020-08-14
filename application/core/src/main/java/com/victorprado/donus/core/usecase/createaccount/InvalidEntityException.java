package com.victorprado.donus.core.usecase.createaccount;

public class InvalidEntityException extends RuntimeException {

    public InvalidEntityException() {
        super("Entity failed to pass the validation");
    }
}
