package rest.bankaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.entity.TransactionType;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.CustomerAlreadyHasAccountException;
import com.victorprado.donus.core.usecase.createaccount.CustomerNotFoundException;
import com.victorprado.donus.core.usecase.createaccount.InvalidEntityException;
import com.victorprado.donus.core.usecase.performtransaction.BankAccountNotFoundException;
import com.victorprado.donus.core.usecase.performtransaction.InsufficientBankAccountBalanceException;
import com.victorprado.donus.core.usecase.performtransaction.PerformTransactionUseCase;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BankAccountEndpointTest {

    private final CreateAccountUseCase createAccountUseCase = mock(CreateAccountUseCase.class);
    private final PerformTransactionUseCase performTransactionUseCase = mock(PerformTransactionUseCase.class);
    private final BankAccountEndpoint bankAccountEndpoint = new BankAccountEndpoint(createAccountUseCase, performTransactionUseCase);

    @Test
    public void shouldReturnSuccessWhenCreatingAccount() {
        givenValidAccountCreated();

        ResponseEntity<Response> response = bankAccountEndpoint.createAccount(new CustomerDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.SUCCESS);
    }

    @Test
    public void shouldReturnNotFoundWhenCustomerDoenstExist() {
        givenCustomerDoenstExist();

        CustomerDTO dto = new CustomerDTO();
        dto.setCpf("00000000000");

        ResponseEntity<Response> response = bankAccountEndpoint.createAccount(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    @Test
    public void shouldReturnUnprocessableWhenCustomerIsInvalid() {
        givenInvalidCustomer();

        ResponseEntity<Response> response = bankAccountEndpoint.createAccount(new CustomerDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    @Test
    public void shouldNotCreateAccountForCustomerThatAlreadyHasAccount() {
        givenCustomerThatAlreadyHasAccount();

        ResponseEntity<Response> response = bankAccountEndpoint.createAccount(new CustomerDTO());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    @Test
    public void shouldMakeTransferWithSuccess() {
        givenTransactionPerformed();

        TransferTransactionDTO dto = new TransferTransactionDTO();
        dto.setDestinationAccountNumber("5235453");
        dto.setValue(100D);
        dto.setWhen(LocalDateTime.now().toString());

        ResponseEntity<Response> response = bankAccountEndpoint.makeTransfer("23121", dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.SUCCESS);
    }

    @Test
    public void shouldNotMakeTransferWithAccountDoenstExist() {
        givenTransactionNotPerformedDueToAccountNonexistent();

        TransferTransactionDTO dto = new TransferTransactionDTO();
        dto.setDestinationAccountNumber("5235453");
        dto.setValue(100D);
        dto.setWhen(LocalDateTime.now().toString());

        ResponseEntity<Response> response = bankAccountEndpoint.makeTransfer("23121", dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    @Test
    public void shouldNotMakeTransferWithAccountDoenstHaveEnoughBalance() {
        givenTransactionNotPerformedDueToInsufficientFunds();

        TransferTransactionDTO dto = new TransferTransactionDTO();
        dto.setDestinationAccountNumber("5235453");
        dto.setValue(100D);
        dto.setWhen(LocalDateTime.now().toString());

        ResponseEntity<Response> response = bankAccountEndpoint.makeTransfer("23121", dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(Objects.requireNonNull(response.getBody()).getStatus()).isEqualTo(EndpointStatus.ERROR);
    }

    private void givenValidAccountCreated() {
        when(createAccountUseCase.create(anyObject())).thenReturn(generateBankAccount());
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

    private void givenTransactionPerformed() {
        when(performTransactionUseCase.transfer(anyString(), anyString(), anyDouble())).thenReturn(generateBankTransaction());
    }

    private void givenTransactionNotPerformedDueToAccountNonexistent() {
        when(performTransactionUseCase.transfer(anyString(), anyString(), anyDouble())).thenThrow(BankAccountNotFoundException.class);
    }

    private void givenTransactionNotPerformedDueToInsufficientFunds() {
        when(performTransactionUseCase.transfer(anyString(), anyString(), anyDouble())).thenThrow(InsufficientBankAccountBalanceException.class);
    }

    private BankAccount generateBankAccount() {
        Customer customer = new Customer();
        customer.setCpf("00000000000");
        BankAccount bankAccount = new BankAccount(customer);
        bankAccount.updateLastMofiedDate();
        bankAccount.generateCreatedDate();
        return bankAccount;
    }

    private BankTransaction generateBankTransaction() {
        return new BankTransaction.Builder()
                .sourceAccount(generateBankAccount())
                .destinationAccount(generateBankAccount())
                .value(100D)
                .type(TransactionType.TRANSFER)
                .when(LocalDateTime.now())
                .build();
    }
}
