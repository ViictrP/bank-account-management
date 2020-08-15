package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.exception.DataProviderException;

@FunctionalInterface
public interface CreateBankAccount {

    void createAccount(BankAccount account) throws DataProviderException;
}
