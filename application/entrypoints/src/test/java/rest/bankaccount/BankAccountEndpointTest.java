package rest.bankaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.CustomerAlreadyHasAccountException;
import com.victorprado.donus.core.usecase.createaccount.CustomerNotFoundException;
import com.victorprado.donus.core.usecase.createaccount.InvalidEntityException;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BankAccountEndpointTest {

    private final CreateAccountUseCase createAccountUseCase = mock(CreateAccountUseCase.class);
    private final BankAccountEndpoint bankAccountEndpoint = new BankAccountEndpoint(createAccountUseCase);

    @Test
    public void shouldReturnSuccessWhenCreatingAccount() {
        givenValidAccountCreated();

        ResponseEntity<BankAccountDTO> response = bankAccountEndpoint.create(new CustomerDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.SUCCESS);
    }

    @Test
    public void shouldReturnNotFoundWhenCustomerDoenstExist() {
        givenCustomerDoenstExist();

        CustomerDTO dto = new CustomerDTO();
        dto.setCpf("00000000000");

        ResponseEntity<BankAccountDTO> response = bankAccountEndpoint.create(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    @Test
    public void shouldReturnUnprocessableWhenCustomerIsInvalid() {
        givenInvalidCustomer();

        ResponseEntity<BankAccountDTO> response = bankAccountEndpoint.create(new CustomerDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    @Test
    public void shouldNotCreateAccountForCustomerThatAlreadyHasAccount() {
        givenCustomerThatAlreadyHasAccount();

        ResponseEntity<BankAccountDTO> response = bankAccountEndpoint.create(new CustomerDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    private void givenValidAccountCreated() {
        when(createAccountUseCase.create(anyObject())).thenReturn(generateEntity());
    }

    private void givenCustomerDoenstExist() {
        when(createAccountUseCase.create(anyObject())).thenThrow(CustomerNotFoundException.class);
    }

    private void givenInvalidCustomer() {
        when(createAccountUseCase.create(anyObject())).thenThrow(InvalidEntityException.class);
    }

    private void givenCustomerThatAlreadyHasAccount() {
        when(createAccountUseCase.create(anyObject())).thenThrow(CustomerAlreadyHasAccountException.class);
    }

    private BankAccount generateEntity() {
        Customer customer = new Customer();
        customer.setCpf("00000000000");
        BankAccount bankAccount = new BankAccount(customer);
        bankAccount.updateLastMofiedDate();
        bankAccount.generateCreatedDate();
        return bankAccount;
    }
}
