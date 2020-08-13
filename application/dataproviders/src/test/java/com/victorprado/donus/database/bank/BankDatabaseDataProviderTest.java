package com.victorprado.donus.database.bank;

import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.createaccount.DataProviderException;
import org.junit.Test;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BankDatabaseDataProviderTest {

    JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

    Customer customerEntity = new Customer("12rqedf", "Victor Prado", "00000000000");
    Customer validCustomer = new Customer("2318386", "André Giorgiani", "00000000001");
    Customer invalidCustomer = new Customer("2318386", "André Giorgiani", null);

    BankDatabaseDataProvider bankDatabaseDataProvider = new BankDatabaseDataProvider(jdbcTemplate);

    @Test
    public void shouldGetCustomerDetailsWithSuccess() {
        givenACustomerThatExists();

        Optional<Customer> customer = bankDatabaseDataProvider.getOne("00000000000");

        assertThat(customer).isPresent();
        assertThat(customer.get().getId()).isEqualTo(customerEntity.getId());
    }

    @Test
    public void shouldGetCustomerDetailsAndReturnEmpty() {
        givenACustomerThatDoesNotExists();

        Optional<Customer> customer = bankDatabaseDataProvider.getOne("00000000000");

        assertThat(customer).isEmpty();
    }

    @Test
    public void shouldCreateACustomerWithSuccess() throws Exception {
        bankDatabaseDataProvider.register(validCustomer);
        verify(jdbcTemplate).update(anyObject(), eq("2318386"), eq("00000000001"), eq("André Giorgiani"));
    }

    @Test(expected = DataProviderException.class)
    public void shouldThrowExceptionWhenCreateACustomer() throws Exception {
        givenACustomerCreationWithException();

        bankDatabaseDataProvider.register(validCustomer);
    }

    private void givenACustomerThatExists() {
        when(jdbcTemplate.queryForObject(anyObject(), eq(Customer.class), eq("00000000000"))).thenReturn(customerEntity);
    }

    private void givenACustomerThatDoesNotExists() {
        when(jdbcTemplate.queryForObject(anyObject(), eq(Customer.class), eq("00000000000"))).thenThrow(InvalidResultSetAccessException.class);
    }

    private void givenACustomerCreationWithException() {
        when(jdbcTemplate.update(anyObject(), anyString(), anyString(), anyString())).thenThrow(InvalidResultSetAccessException.class);
    }
}
