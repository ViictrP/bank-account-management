package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.Customer;

import java.util.Optional;

@FunctionalInterface
public interface GetCustomer {

    Optional<Customer> getOne(String cpf);
}
