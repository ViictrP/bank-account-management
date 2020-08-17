package com.victorprado.donus.core.entity;

import com.victorprado.donus.core.usecase.performtransaction.InsufficientBankAccountBalanceException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BankAccountTest {

    @Test
    public void shouldIncreaseBalanceWithSuccess() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.ZERO);
        BigDecimal value = BigDecimal.valueOf(10.0);
        bankAccount.increaseBalance(value);

        assertThat(bankAccount.getBalance()).isEqualByComparingTo(value);
    }

    @Test
    public void shouldReduceBalance() {
        BankAccount bankAccount = new BankAccount();
        BigDecimal value = BigDecimal.valueOf(10.0);
        bankAccount.setBalance(value);
        bankAccount.reduceBalance(value);

        assertThat(bankAccount.getBalance()).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test(expected = InsufficientBankAccountBalanceException.class)
    public void shouldNotReduceInsufficientBalance() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.ZERO);
        BigDecimal value = BigDecimal.valueOf(10.0);
        bankAccount.reduceBalance(value);

    }
}
