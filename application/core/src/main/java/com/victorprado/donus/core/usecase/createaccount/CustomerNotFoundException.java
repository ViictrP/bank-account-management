package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.exception.CoreException;

public class CustomerNotFoundException extends CoreException {

    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
