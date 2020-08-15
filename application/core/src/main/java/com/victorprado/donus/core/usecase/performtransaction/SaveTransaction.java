package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.exception.DataProviderException;

public interface SaveTransaction {

    void saveTransaction(BankTransaction transaction) throws DataProviderException;
}
