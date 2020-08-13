package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;

public class CreateAccountUseCase {

    private final ManageCustomer manageCustomer;

    public CreateAccountUseCase(ManageCustomer manageCustomer) {
        this.manageCustomer = manageCustomer;
    }

    public BankAccount create(Customer customer) {
        Customer customerEntity = manageCustomer.getOne(customer.getCpf())
                .orElseThrow(() -> new CustomerNotFoundException());



        return null;
    }
}
