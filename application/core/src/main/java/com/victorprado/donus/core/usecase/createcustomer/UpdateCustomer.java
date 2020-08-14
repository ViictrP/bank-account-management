package com.victorprado.donus.core.usecase.createcustomer;

import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.DataProviderException;

public interface UpdateCustomer {

    void update(Customer customer)  throws DataProviderException;
}
