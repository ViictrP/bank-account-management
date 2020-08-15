package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.exception.DataProviderException;

import java.math.BigDecimal;

public interface UpdateBankAccountBalance {

    void updateBalance(BankAccount bankAccount, BigDecimal newValue) throws DataProviderException;
}
