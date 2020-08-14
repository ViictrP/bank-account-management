package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.condition.NullObjectException;
import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateAccountUseCaseTest {

    GetCustomer getCustomer = mock(GetCustomer.class);
    CreateBankAccount createBankAccount = mock(CreateBankAccount.class);
    CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(getCustomer, createBankAccount);

    Customer customerThatDoesNotHaveAccount = new Customer("Victor Prado", "00000000000");
    Customer customerThatDoesNotExists = new Customer("Ronaldo Prates", "00000000001");
    Customer customerThatDoesHaveAccount = new Customer("WellingtonGomes", "00000000002");

    @Test
    public void shouldCreateAccountWithSuccess() {
        givenACustomerThatDoesNotHaveAccount();

        BankAccount account = createAccountUseCase.create(customerThatDoesNotHaveAccount);

        assertThat(account).isNotNull();
        assertThat(account.getBalance()).isZero();
        assertThat(account.getCustomer().getId()).isEqualTo(customerThatDoesNotHaveAccount.getId());
    }

    @Test(expected = CustomerNotFoundException.class)
    public void shouldNotCreateAccountIfCustomerDoenstExist() {
        givenACustomerThatDoesNotExists();

        createAccountUseCase.create(customerThatDoesNotExists);
    }

    @Test(expected = InvalidEntityException.class)
    public void shouldNotCreateAccountWithInvalidCustomer() {
        Customer customer = new Customer();
        createAccountUseCase.create(customer);
    }

    @Test(expected = NullObjectException.class)
    public void shouldNotCreateAccountWithNullCustomer() {
        createAccountUseCase.create(null);
    }

    private void givenACustomerThatDoesNotHaveAccount() {
        when(getCustomer.getOne(anyString())).thenReturn(Optional.of(customerThatDoesNotHaveAccount));
    }

    private void givenACustomerThatDoesNotExists() {
        when(getCustomer.getOne(anyString())).thenReturn(Optional.empty());
    }

    private void givenACustomerThatDoesHaveAccount() {
        when(getCustomer.getOne(anyString())).thenReturn(Optional.of(customerThatDoesHaveAccount));
    }
}
