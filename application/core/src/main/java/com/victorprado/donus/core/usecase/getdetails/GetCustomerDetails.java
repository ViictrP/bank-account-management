package com.victorprado.donus.core.usecase.getdetails;

import com.victorprado.donus.core.entity.Customer;

public interface GetCustomerDetails {

    Customer getDetails(String cpf);
}
