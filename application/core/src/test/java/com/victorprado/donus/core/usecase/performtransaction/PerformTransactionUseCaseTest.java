package com.victorprado.donus.core.usecase.performtransaction;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.entity.TransactionType;
import com.victorprado.donus.core.usecase.createaccount.GetBankAccount;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PerformTransactionUseCaseTest {

    private final GetBankAccount getBankAccount = mock(GetBankAccount.class);
    private final UpdateBankAccountBalance updateBankAccountBalance = mock(UpdateBankAccountBalance.class);
    private final SaveTransaction saveTransaction = mock(SaveTransaction.class);

    private final PerformTransactionUseCase performTransactionUseCase = new PerformTransactionUseCase(getBankAccount, updateBankAccountBalance, saveTransaction);

    BankAccount account1 = new BankAccount(new Customer("1234", "Person 1", "00000000000"));
    BankAccount account2 = new BankAccount(new Customer("5678", "Person 2", "00000000001"));

    @Test
    public void shouldPerformMoneyTransferWithSuccess() {
        givenAccountsThatExists();

        Double transferValue = 100.00D;

        BankTransaction transaction = performTransactionUseCase.transfer("12345", "67890", transferValue);

        assertThat(transaction).isNotNull();
        assertThat(transaction.getSourceAccount().getId()).isEqualTo(account1.getId());
        assertThat(transaction.getDestinationAccount().getId()).isEqualTo(account2.getId());
        assertThat(transaction.getType()).isEqualTo(TransactionType.TRANSFER);
        assertThat(transaction.getWhen()).isNotNull();
        assertThat(transaction.getValue()).isEqualTo(transferValue);

        BankAccount sourceAccount = transaction.getSourceAccount();
        BankAccount destinationAccount = transaction.getDestinationAccount();

        assertThat(sourceAccount.getBalance()).isZero();
        assertThat(destinationAccount.getBalance()).isEqualTo(transferValue);
    }

    @Test(expected = BankAccountNotFoundException.class)
    public void shouldNotPerformTransformForSourceAccountThatDoenstExists() {
        givenSourceAccountThatDoenstExists();

        Double transferValue = 100.00D;

        performTransactionUseCase.transfer("12345", "67890", transferValue);
    }

    @Test(expected = BankAccountNotFoundException.class)
    public void shouldNotPerformTransformForDestinationAccountThatDoenstExists() {
        givenDestinationAccountThatDoenstExists();

        Double transferValue = 100.00D;

        performTransactionUseCase.transfer("12345", "67890", transferValue);
    }

    @Test(expected = InsufficientBankAccountBalanceException.class)
    public void shouldNotPerformTransferTransactionWithInsufficientAccountBalance() {
        givenAccountsWithInsufficientFunds();
        Double transferValue = 100.00D;

        performTransactionUseCase.transfer("12345", "67890", transferValue);
    }

    private void givenAccountsThatExists() {
        account1.setNumber("12345");
        account1.setBalance(100.00);

        account2.setNumber("67890");

        when(getBankAccount.getAccountByNumber(eq("12345"))).thenReturn(Optional.of(account1));
        when(getBankAccount.getAccountByNumber(eq("67890"))).thenReturn(Optional.of(account2));
    }

    private void givenSourceAccountThatDoenstExists() {
        account2.setNumber("67890");

        when(getBankAccount.getAccountByNumber(eq("12345"))).thenReturn(Optional.empty());
        when(getBankAccount.getAccountByNumber(eq("67890"))).thenReturn(Optional.of(account2));
    }

    private void givenDestinationAccountThatDoenstExists() {
        account1.setNumber("12345");
        account2.setNumber("67890");

        when(getBankAccount.getAccountByNumber(eq("12345"))).thenReturn(Optional.of(account1));
        when(getBankAccount.getAccountByNumber(eq("67890"))).thenReturn(Optional.empty());
    }

    private void givenAccountsWithInsufficientFunds() {
        account1.setBalance(0.00);
        account1.setNumber("12345");
        account2.setNumber("67890");

        when(getBankAccount.getAccountByNumber(eq("12345"))).thenReturn(Optional.of(account1));
        when(getBankAccount.getAccountByNumber(eq("67890"))).thenReturn(Optional.of(account2));
    }
}
