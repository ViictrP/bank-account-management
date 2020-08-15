package com.victorprado.donus.database.bank;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.entity.TransactionType;
import com.victorprado.donus.core.exception.DataProviderException;
import com.victorprado.donus.database.rowmapper.BankAccountRowMapper;
import com.victorprado.donus.database.rowmapper.CustomerRowMapper;
import org.junit.Test;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BankDatabaseDataProviderTest {

    JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

    Customer customerEntity = new Customer("12rqedf", "Victor Prado", "00000000000");

    BankDatabaseDataProvider bankDatabaseDataProvider = new BankDatabaseDataProvider(jdbcTemplate);

    @Test
    public void shouldGetCustomerDetailsWithSuccess() {
        givenCustomerThatExists();

        Optional<Customer> customer = bankDatabaseDataProvider.getCustomer("00000000000");

        assertThat(customer).isPresent();
        assertThat(customer.get().getId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void shouldGetCustomerDetailsAndReturnEmpty() {
        givenCustomerThatDoesNotExists();

        Optional<Customer> customer = bankDatabaseDataProvider.getCustomer("00000000000");

        assertThat(customer).isEmpty();
    }

    @Test
    public void shouldCreateAccountWithSuccess() {
        BankAccount bankAccount = new BankAccount(customerEntity);
        bankDatabaseDataProvider.createAccount(bankAccount);

        verify(jdbcTemplate).update(anyObject(), eq(bankAccount.getId()), eq(customerEntity.getId()), eq(bankAccount.getNumber()), eq(bankAccount.getBalance()), eq(bankAccount.getCreatedDate()), eq(bankAccount.getLastModifiedDate()), eq(bankAccount.isDeleted()));
        assertThat(bankAccount.getCreatedDate()).isNotNull();
        assertThat(bankAccount.getLastModifiedDate()).isNotNull();
    }

    @Test(expected = DataProviderException.class)
    public void shouldThrowErrorWhenCreateAccount() {
        givenAccountCreationWithException();
        BankAccount bankAccount = new BankAccount(customerEntity);
        bankDatabaseDataProvider.createAccount(bankAccount);
    }

    @Test
    public void shouldGetAccountForCustomerWithSuccess() {
        givenAccountThatExists();

        Optional<BankAccount> bankAccount = bankDatabaseDataProvider.getAccount("12345asdfg");

        assertThat(bankAccount).isPresent();
        assertThat(bankAccount.get().getCustomer().getId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void shouldGetAccountByNumberWithSuccess() {
        givenAccountThatExists();

        Optional<BankAccount> bankAccount = bankDatabaseDataProvider.getAccountByNumber("12345asdfg");

        assertThat(bankAccount).isPresent();
        assertThat(bankAccount.get().getCustomer().getId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void shouldThrowErrorWhenGetAccountThatDoenstExist() {
        givenAccountThatDoenstExists();

        Optional<BankAccount> bankAccount = bankDatabaseDataProvider.getAccount("12345asdfg");

        assertThat(bankAccount).isEmpty();
    }

    @Test
    public void shouldThrowErrorWhenGetAccountByNumberThatDoenstExist() {
        givenAccountThatDoenstExists();

        Optional<BankAccount> bankAccount = bankDatabaseDataProvider.getAccountByNumber("12345asdfg");

        assertThat(bankAccount).isEmpty();
    }

    @Test
    public void shouldUpdateAccountBalanceWithSuccess() {
        BankAccount account = new BankAccount(customerEntity);
        Double balance = 10_000.00D;

        bankDatabaseDataProvider.updateBalance(account, balance);
        verify(jdbcTemplate).update(anyString(), eq(balance), eq(account.getId()));

        assertThat(account.getBalance()).isEqualTo(balance);

    }

    @Test(expected = DataProviderException.class)
    public void shouldThrowErrorWhenUpdateBalance() {
        givenBalanceUpdateWithException();

        BankAccount account = new BankAccount(customerEntity);
        Double balance = 10_000.00D;

        bankDatabaseDataProvider.updateBalance(account, balance);
    }

    @Test
    public void shouldSaveTransactionWithSuccess() {
        BankTransaction bankTransaction = new BankTransaction.Builder()
                .sourceAccount(new BankAccount(customerEntity))
                .destinationAccount(new BankAccount(customerEntity))
                .type(TransactionType.TRANSFER)
                .value(100D)
                .when(LocalDateTime.now())
                .build();

        bankDatabaseDataProvider.saveTransaction(bankTransaction);

        verify(jdbcTemplate).update(anyObject(), eq(bankTransaction.getId()), eq(bankTransaction.getType().getDescription()), eq(bankTransaction.getWhen()), eq(bankTransaction.getSourceAccount().getId()), eq(bankTransaction.getDestinationAccount().getId()), eq(bankTransaction.getValue()));
    }

    @Test(expected = DataProviderException.class)
    public void shouldSaveTransactionWithException() {
        givenTransactionSaveWithException();

        BankTransaction bankTransaction = new BankTransaction.Builder()
                .sourceAccount(new BankAccount(customerEntity))
                .destinationAccount(new BankAccount(customerEntity))
                .type(TransactionType.TRANSFER)
                .value(100D)
                .when(LocalDateTime.now())
                .build();

        bankDatabaseDataProvider.saveTransaction(bankTransaction);
    }

    private void givenCustomerThatExists() {
        when(jdbcTemplate.queryForObject(anyObject(), any(CustomerRowMapper.class), eq("00000000000"))).thenReturn(customerEntity);
    }

    private void givenCustomerThatDoesNotExists() {
        when(jdbcTemplate.queryForObject(anyObject(), any(CustomerRowMapper.class), eq("00000000000"))).thenThrow(InvalidResultSetAccessException.class);
    }

    private void givenAccountCreationWithException() {
        when(jdbcTemplate.update(anyObject(), anyString(), anyString(), anyString(), anyString(),  anyString(), anyString(), anyString())).thenThrow(InvalidResultSetAccessException.class);
    }

    private void givenAccountThatExists() {
        BankAccount bankAccount = new BankAccount(customerEntity);
        when(jdbcTemplate.queryForObject(anyObject(), any(BankAccountRowMapper.class), eq("12345asdfg"))).thenReturn(bankAccount);
    }

    private void givenAccountThatDoenstExists() {
        when(jdbcTemplate.queryForObject(anyObject(), any(BankAccountRowMapper.class), eq("12345asdfg"))).thenThrow(InvalidResultSetAccessException.class);
    }

    private void givenBalanceUpdateWithException() {
        when(jdbcTemplate.update(anyObject(), anyString(), anyString())).thenThrow(InvalidResultSetAccessException.class);
    }

    private void givenTransactionSaveWithException() {
        when(jdbcTemplate.update(anyObject(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenThrow(InvalidResultSetAccessException.class);
    }
}
