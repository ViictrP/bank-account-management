package rest.bankaccount;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.createaccount.CreateAccountUseCase;
import com.victorprado.donus.core.usecase.createaccount.CustomerAlreadyHasAccountException;
import com.victorprado.donus.core.usecase.createaccount.CustomerNotFoundException;
import com.victorprado.donus.core.usecase.createaccount.InvalidEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accounts")
public class BankAccountEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountEndpoint.class);

    private final CreateAccountUseCase createAccountUseCase;

    public BankAccountEndpoint(CreateAccountUseCase createAccountUseCase) {
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> create(@RequestBody CustomerDTO customer) {
        LOGGER.info("Create account request received for customer {}", customer.getCpf());
        try {
            BankAccount account = createAccountUseCase.create(toCustomerEntity(customer));
            BankAccountDTO dto = toDTO(account);
            dto.setStatus(EndpointStatus.SUCCESS);
            LOGGER.info("Account creation concluded with success. CPF {}", customer.getCpf());
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (CustomerNotFoundException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidEntityException | CustomerAlreadyHasAccountException error) {
            LOGGER.error(error.getMessage(), error);
            return buildErrorResponse(error.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private ResponseEntity<BankAccountDTO> buildErrorResponse(String message, HttpStatus status) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setStatus(EndpointStatus.ERROR);
        dto.setMessage(message);
        return ResponseEntity.status(status).body(dto);
    }

    private Customer toCustomerEntity(CustomerDTO customerDTO) {
        return new Customer(null, customerDTO.getCpf());
    }

    private BankAccountDTO toDTO(BankAccount account) {
        return new BankAccountDTO.Builder()
                .id(account.getId())
                .customer(account.getCustomer().getCpf())
                .balance(account.getBalance())
                .createdDate(account.getCreatedDate())
                .lastModifiedDate(account.getLastModifiedDate())
                .deleted(account.isDeleted())
                .build();
    }
}
