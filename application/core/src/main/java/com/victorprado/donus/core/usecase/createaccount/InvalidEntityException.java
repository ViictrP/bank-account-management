package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.exception.CoreException;

public class InvalidEntityException extends CoreException {

    public InvalidEntityException() {
        super("Some required fields is missing.");
    }
}
