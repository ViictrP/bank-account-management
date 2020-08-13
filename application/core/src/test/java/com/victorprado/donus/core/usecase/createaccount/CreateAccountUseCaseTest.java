package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.condition.NullObjectException;
import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateAccountUseCaseTest {

    ManageCustomer manageCustomer = mock(ManageCustomer.class);
    ManageBankAccount manageBankAccount = mock(ManageBankAccount.class);
    CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(manageCustomer, manageBankAccount);

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

    @Test
    public void shouldCreateAccountWithSuccessEvenWhenCustomerDoesNotExists() {
        givenACustomerThatDoesNotExists();

        BankAccount account = createAccountUseCase.create(customerThatDoesNotExists);

        assertThat(account).isNotNull();
        assertThat(account.getBalance()).isZero();
        assertThat(account.getCustomer().getId()).isEqualTo(customerThatDoesNotExists.getId());
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
        when(manageCustomer.getOne(anyString())).thenReturn(Optional.of(customerThatDoesNotHaveAccount));
    }

    private void givenACustomerThatDoesNotExists() {
        when(manageCustomer.getOne(anyString())).thenReturn(Optional.empty());
    }

    private void givenACustomerThatDoesHaveAccount() {
        when(manageCustomer.getOne(anyString())).thenReturn(Optional.of(customerThatDoesHaveAccount));
    }
}
