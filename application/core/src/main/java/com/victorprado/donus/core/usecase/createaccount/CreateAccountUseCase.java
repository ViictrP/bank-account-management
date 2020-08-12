package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.getdetails.GetCustomerDetails;

public class CreateAccountUseCase {

    GetCustomerDetails getCustomerDetails;

    public CreateAccountUseCase(GetCustomerDetails getCustomerDetails) {
        this.getCustomerDetails = getCustomerDetails;
    }

    public BankAccount create(Customer customer) {
        return null;
    }
}
