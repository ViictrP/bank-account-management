package com.victorprado.donus.database.bank;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.DataProviderException;
import com.victorprado.donus.database.rowmapper.BankAccountRowMapper;
import com.victorprado.donus.database.rowmapper.CustomerRowMapper;
import org.junit.Test;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

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

        Optional<Customer> customer = bankDatabaseDataProvider.getOne("00000000000");

        assertThat(customer).isPresent();
        assertThat(customer.get().getId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void shouldGetCustomerDetailsAndReturnEmpty() {
        givenCustomerThatDoesNotExists();

        Optional<Customer> customer = bankDatabaseDataProvider.getOne("00000000000");

        assertThat(customer).isEmpty();
    }

    @Test
    public void shouldCreateAccountWithSuccess() {
        BankAccount bankAccount = new BankAccount(customerEntity);
        bankDatabaseDataProvider.create(bankAccount);

        verify(jdbcTemplate).update(anyObject(), eq(bankAccount.getId()), eq(customerEntity.getId()), eq(bankAccount.getNumber()), eq(bankAccount.getBalance()));
        assertThat(bankAccount.getCreatedDate()).isNotNull();
        assertThat(bankAccount.getLastModifiedDate()).isNotNull();
    }

    @Test(expected = DataProviderException.class)
    public void shouldThrowErrorWhenCreateAccount() {
        givenAccountCreationWithException();
        BankAccount bankAccount = new BankAccount(customerEntity);
        bankDatabaseDataProvider.create(bankAccount);
    }

    @Test
    public void shouldGetAccountForCustomerWithSuccess() {
        givenAccountThatExists();

        Optional<BankAccount> bankAccount = bankDatabaseDataProvider.getAccount("12345asdfg");

        assertThat(bankAccount).isPresent();
        assertThat(bankAccount.get().getCustomer().getId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void shouldThrowErrorWhenGetAccountThatDoenstExist() {
        givenAccountThatDoenstExists();

        Optional<BankAccount> bankAccount = bankDatabaseDataProvider.getAccount("12345asdfg");

        assertThat(bankAccount).isEmpty();
    }

    private void givenCustomerThatExists() {
        when(jdbcTemplate.queryForObject(anyObject(), any(CustomerRowMapper.class), eq("00000000000"))).thenReturn(customerEntity);
    }

    private void givenCustomerThatDoesNotExists() {
        when(jdbcTemplate.queryForObject(anyObject(), any(CustomerRowMapper.class) ,eq("00000000000"))).thenThrow(InvalidResultSetAccessException.class);
    }

    private void givenAccountCreationWithException() {
        when(jdbcTemplate.update(anyObject(), anyString(), anyString(), anyString(), anyString())).thenThrow(InvalidResultSetAccessException.class);
    }

    private void givenAccountThatExists() {
        BankAccount bankAccount = new BankAccount(customerEntity);
        when(jdbcTemplate.queryForObject(anyObject(), any(BankAccountRowMapper.class), eq("12345asdfg"))).thenReturn(bankAccount);
    }

    private void givenAccountThatDoenstExists() {
        BankAccount bankAccount = new BankAccount(customerEntity);
        when(jdbcTemplate.queryForObject(anyObject(), any(BankAccountRowMapper.class), eq("12345asdfg"))).thenThrow(InvalidResultSetAccessException.class);
    }
}
