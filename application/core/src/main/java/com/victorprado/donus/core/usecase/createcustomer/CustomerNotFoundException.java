package com.victorprado.donus.core.usecase.createcustomer;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException() {
        super("Customer not found");
    }
}
