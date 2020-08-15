package com.victorprado.donus.database.bank;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.BankTransaction;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.DataProviderException;
import com.victorprado.donus.core.usecase.createaccount.CreateBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetCustomer;
import com.victorprado.donus.core.usecase.performtransaction.SaveTransaction;
import com.victorprado.donus.core.usecase.performtransaction.UpdateBankAccountBalance;
import com.victorprado.donus.database.exception.DataProviderInsertException;
import com.victorprado.donus.database.rowmapper.BankAccountRowMapper;
import com.victorprado.donus.database.rowmapper.CustomerRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class BankDatabaseDataProvider implements GetCustomer, CreateBankAccount, GetBankAccount, UpdateBankAccountBalance, SaveTransaction {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDatabaseDataProvider.class);

    private final JdbcTemplate jdbcTemplate;

    public BankDatabaseDataProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> getCustomer(String cpf) {
        try {
            Customer customerEntity = jdbcTemplate.queryForObject("SELECT c.* FROM donus.customer c WHERE c.cpf = ?", new CustomerRowMapper() , cpf);
            return Optional.ofNullable(customerEntity);
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void createAccount(@NonNull BankAccount account) throws DataProviderException {
        try {
            account.generateCreatedDate();
            account.updateLastMofiedDate();
            jdbcTemplate.update("INSERT INTO donus.bank_account(id, customer_id, number, balance, created_date, last_modified_date, deleted) VALUES(?,?,?,?,?,?,?)", account.getId(), account.getCustomer().getId(), account.getNumber(), account.getBalance(), account.getCreatedDate(), account.getLastModifiedDate(), account.isDeleted());
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            throw new DataProviderInsertException();
        }
    }

    @Override
    public Optional<BankAccount> getAccount(String customerId) {
        try {
            BankAccount accountEntity = jdbcTemplate.queryForObject("SELECT ac.* FROM donus.bank_account ac WHERE ac.customer_id = ?", new BankAccountRowMapper() , customerId);
            return Optional.ofNullable(accountEntity);
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<BankAccount> getAccountByNumber(String accountNumber) {
        try {
            BankAccount accountEntity = jdbcTemplate.queryForObject("SELECT ac.* FROM donus.bank_account ac WHERE ac.number = ?", new BankAccountRowMapper() , accountNumber);
            return Optional.ofNullable(accountEntity);
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void updateBalance(BankAccount bankAccount, Double newValue) throws DataProviderException {
        try {
            jdbcTemplate.update("UPDATE donus.bank_account SET balance = ? WHERE id = ?", newValue, bankAccount.getId());
            bankAccount.setBalance(newValue);
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            throw new DataProviderInsertException();
        }
    }

    @Override
    public void saveTransaction(@NonNull BankTransaction transaction) throws DataProviderException {
        try {
            jdbcTemplate.update(
                    "INSERT INTO donus.bank_transaction(id, type, transaction_date, source_account_id, destination_account_id, amount) VALUES(?,?,?,?,?,?)",
                    transaction.getId(), transaction.getType().getDescription(), transaction.getWhen(), transaction.getSourceAccount().getId(), transaction.getDestinationAccount().getId(), transaction.getValue()
            );
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            throw new DataProviderInsertException();
        }
    }
}
