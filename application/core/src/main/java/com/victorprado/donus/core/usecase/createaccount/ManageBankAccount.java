package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;

public interface ManageBankAccount {

    void create(BankAccount account) throws DataProviderException;
}
