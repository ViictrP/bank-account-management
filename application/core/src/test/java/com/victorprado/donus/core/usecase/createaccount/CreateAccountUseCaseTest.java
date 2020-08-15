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
    GetBankAccount getBankAccount = mock(GetBankAccount.class);
    CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(getCustomer, createBankAccount, getBankAccount);

    Customer customerThatDoesNotHaveAccount = new Customer("Victor Prado", "00000000000");
    Customer customerThatDoesNotExists = new Customer("Ronaldo Prates", "00000000001");
    Customer customerThatDoesHaveAccount = new Customer("WellingtonGomes", "00000000002");

    @Test
    public void shouldCreateAccountWithSuccess() {
        givenAccountThatDoenstExists();
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

    @Test(expected = CustomerAlreadyHasAccountException.class)
    public void shouldNotCreateAccountIfCustomerAlreadyHasOne() {
        givenACustomerThatExists();
        givenACustomerThatHasAccount();

        Customer customer = new Customer("dasdhi12", "Victor Prado", "00000000000");
        createAccountUseCase.create(customer);
    }

    @Test(expected = NullObjectException.class)
    public void shouldNotCreateAccountWithNullCustomer() {
        createAccountUseCase.create(null);
    }

    private void givenACustomerThatDoesNotHaveAccount() {
        when(getCustomer.getCustomer(anyString())).thenReturn(Optional.of(customerThatDoesNotHaveAccount));
    }

    private void givenACustomerThatDoesNotExists() {
        when(getCustomer.getCustomer(anyString())).thenReturn(Optional.empty());
    }

    private void givenACustomerThatExists() {
        when(getCustomer.getCustomer(anyString())).thenReturn(Optional.of(customerThatDoesNotHaveAccount));
    }

    private void givenAccountThatDoenstExists() {
        when(getBankAccount.getAccount(anyString())).thenReturn(Optional.empty());
    }

    private void givenACustomerThatHasAccount() {
        when(getBankAccount.getAccount(anyString())).thenReturn(Optional.of(new BankAccount()));
    }
}
