package rest.bankaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.CustomerAlreadyHasAccountException;
import com.victorprado.donus.core.usecase.createaccount.CustomerNotFoundException;
import com.victorprado.donus.core.usecase.createaccount.InvalidEntityException;
import com.victorprado.donus.core.usecase.performtransaction.BankAccountNotFoundException;
import com.victorprado.donus.core.usecase.performtransaction.InsufficientBankAccountBalanceException;
import com.victorprado.donus.core.usecase.performtransaction.PerformTransactionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/accounts")
public class BankAccountEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountEndpoint.class);

    private final CreateAccountUseCase createAccountUseCase;
    private final PerformTransactionUseCase performTransactionUseCase;

    public BankAccountEndpoint(CreateAccountUseCase createAccountUseCase, PerformTransactionUseCase performTransactionUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.performTransactionUseCase = performTransactionUseCase;
    }

    @PostMapping
    public ResponseEntity<Response> createAccount(@RequestBody CustomerDTO customer) {
        LOGGER.info("Create account request received for customer {}", customer.getCpf());
        try {
            BankAccount account = createAccountUseCase.create(toCustomerEntity(customer));
            BankAccountDTO dto = toAccountDTO(account);
            Response response = new Response();
            response.setStatus(EndpointStatus.SUCCESS);
            response.setData(dto);
            LOGGER.info("Account creation concluded with success. CPF {}", customer.getCpf());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CustomerNotFoundException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidEntityException | CustomerAlreadyHasAccountException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("{accountNumber}/transfers")
    public ResponseEntity<Response> makeTransfer(@PathVariable String accountNumber, @RequestBody TransferTransactionDTO transferTransactionDTO) {
        LOGGER.info("Transfer transaction request received from account {} to account {} with value {}", accountNumber, transferTransactionDTO.getDestinationAccountNumber(), transferTransactionDTO.getValue());
        try {
            BankTransaction transaction = performTransactionUseCase.transfer(accountNumber, transferTransactionDTO.getDestinationAccountNumber(), transferTransactionDTO.getValue());
            BankTransactionDTO dto = toTransactionDTO(transaction);
            Response response = new Response();
            response.setStatus(EndpointStatus.SUCCESS);
            response.setData(dto);
            LOGGER.info("Transaction performed. transaction identification {}", transaction.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BankAccountNotFoundException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InsufficientBankAccountBalanceException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/{accountNumber}/withdraws")
    public ResponseEntity<Response> doWithdraw(@PathVariable String accountNumber, @RequestBody WithdrawDTO withdrawDTO) {
        LOGGER.info("Withdraw transaction request received from account {} with value {}", accountNumber, withdrawDTO.getValue());
        try {
            BankTransaction transaction = performTransactionUseCase.withdraw(accountNumber, withdrawDTO.getValue());
            BankTransactionDTO dto = toTransactionDTO(transaction);
            Response response = new Response();
            response.setStatus(EndpointStatus.SUCCESS);
            response.setData(dto);
            LOGGER.info("Withdraw performed. transaction identification {}", transaction.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BankAccountNotFoundException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InsufficientBankAccountBalanceException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @PostMapping("/{accountNumber}/deposits")
    public ResponseEntity<Response> deposit(@PathVariable String accountNumber, @RequestBody DepositDTO depositDTO) {
        LOGGER.info("Deposit transaction request received into account {} with value {}", accountNumber, depositDTO.getValue());
        try {
            BankTransaction transaction = performTransactionUseCase.deposit(accountNumber, depositDTO.getValue());
            BankTransactionDTO dto = toTransactionDTO(transaction);
            Response response = new Response();
            response.setStatus(EndpointStatus.SUCCESS);
            response.setData(dto);
            LOGGER.info("Deposit performed. transaction identification {}", transaction.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BankAccountNotFoundException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<Response> buildErrorResponse(String message, HttpStatus status) {
        Response response = new Response();
        response.setStatus(EndpointStatus.ERROR);
        response.setMessage(message);
        response.setData(null);
        return ResponseEntity.status(status).body(response);
    }

    private Customer toCustomerEntity(CustomerDTO customerDTO) {
        return new Customer(customerDTO.getName(), customerDTO.getCpf());
    }

    private BankAccountDTO toAccountDTO(BankAccount account) {
        return new BankAccountDTO.Builder()
                .id(account.getId())
                .customer(account.getCustomer().getCpf())
                .number(account.getNumber())
                .balance(account.getBalance())
                .createdDate(account.getCreatedDate())
                .lastModifiedDate(account.getLastModifiedDate())
                .deleted(account.isDeleted())
                .build();
    }

    private BankTransactionDTO toTransactionDTO(BankTransaction transaction) {
        BankAccount destinationAccount = transaction.getDestinationAccount();
        return new BankTransactionDTO.Builder()
                .id(transaction.getId())
                .sourceAccount(transaction.getSourceAccount().getNumber())
                .destinationAccount(destinationAccount != null ? destinationAccount.getNumber() : null)
                .type(transaction.getType().getDescription())
                .value(transaction.getValue())
                .when(transaction.getWhen())
                .build();
    }
}
