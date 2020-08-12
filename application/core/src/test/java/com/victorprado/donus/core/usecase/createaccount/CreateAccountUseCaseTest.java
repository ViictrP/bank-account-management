package com.victorprado.donus.core.usecase.createaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.getdetails.GetCustomerDetails;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateAccountUseCaseTest {

    GetCustomerDetails getCustomerDetails = mock(GetCustomerDetails.class);
    CreateAccountUseCase createAccountUseCase = new CreateAccountUseCase(getCustomerDetails);

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

    private void givenACustomerThatDoesNotHaveAccount() {
        when(getCustomerDetails.getDetails(anyString())).thenReturn(customerThatDoesNotHaveAccount);
    }

    private void givenACustomerThatDoesNotExists() {
        when(getCustomerDetails.getDetails(anyString())).thenReturn(null);
    }

    private void givenACustomerThatDoesHaveAccount() {
        when(getCustomerDetails.getDetails(anyString())).thenReturn(customerThatDoesHaveAccount);
    }
}
