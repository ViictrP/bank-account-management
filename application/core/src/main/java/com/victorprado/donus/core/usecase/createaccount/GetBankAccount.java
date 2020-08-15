package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;

import java.util.Optional;

public interface GetBankAccount {

    Optional<BankAccount> getAccount(String customerId);
    Optional<BankAccount> getAccountByNumber(String accountNumber);
}
