package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.exception.DataProviderException;

public interface UpdateBankAccountBalance {

    void updateBalance(BankAccount bankAccount, Double newValue) throws DataProviderException;
}
