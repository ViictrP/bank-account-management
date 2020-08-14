package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.exception.DataProviderException;

@FunctionalInterface
public interface CreateBankAccount {

    void create(BankAccount account) throws DataProviderException;
}
