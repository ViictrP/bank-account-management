package com.victorprado.donus.core.usecase.createcustomer;

import com.victorprado.donus.core.entity.Customer;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateCustomerUseCaseTest {

    GetCustomer getCustomer = mock(GetCustomer.class);
    CreateCustomer createCustomer = mock(CreateCustomer.class);
    UpdateCustomer updateCustomer = mock(UpdateCustomer.class);

    Customer validCustomer = new Customer("Victor Prado", "00000000000");

    CreateCustomerUseCase createCustomerUseCase = new CreateCustomerUseCase(getCustomer, createCustomer, updateCustomer);

    @Test
    public void shouldCreateCustomerWithSuccess() {
        givenACustomerThatDoenstExist();

        Customer customer = createCustomerUseCase.create(validCustomer);

        assertThat(customer.getId()).isEqualTo(validCustomer.getId());
        assertThat(customer.getName()).isEqualTo(validCustomer.getName());
        assertThat(customer.getCpf()).isEqualTo(validCustomer.getCpf());
    }

    @Test(expected = CustomerAlreadyExist.class)
    public void shouldThrowErrorForExistingCustomer() {
        givenACustomerThatExist();

        createCustomerUseCase.create(validCustomer);
    }

    private void givenACustomerThatDoenstExist() {
        when(getCustomer.getOne(anyString())).thenReturn(Optional.empty());
    }

    private void givenACustomerThatExist() {
        when(getCustomer.getOne(anyString())).thenReturn(Optional.of(validCustomer));
    }
}
