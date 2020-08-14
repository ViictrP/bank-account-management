package com.victorprado.donus.database.bank;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.exception.DataProviderException;
import com.victorprado.donus.core.usecase.createaccount.CreateBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetBankAccount;
import com.victorprado.donus.core.usecase.createaccount.GetCustomer;
import com.victorprado.donus.database.exception.DataProviderInsertException;
import com.victorprado.donus.database.rowmapper.BankAccountRowMapper;
import com.victorprado.donus.database.rowmapper.CustomerRowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class BankDatabaseDataProvider implements GetCustomer, CreateBankAccount, GetBankAccount {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDatabaseDataProvider.class);

    private final JdbcTemplate jdbcTemplate;

    public BankDatabaseDataProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> getOne(String cpf) {
        try {
            Customer customerEntity = jdbcTemplate.queryForObject("SELECT c.* FROM donus.customer c WHERE c.cpf = ?", new CustomerRowMapper() , cpf);
            return Optional.ofNullable(customerEntity);
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void create(@NonNull BankAccount account) throws DataProviderException {
        try {
            account.generateCreatedDate();
            account.updateLastMofiedDate();
            jdbcTemplate.update("INSERT INTO donus.bank_account(id, customer_id, number, balance) VALUES(?,?,?,?)", account.getId(), account.getCustomer().getId(), account.getNumber(), account.getBalance());
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
}
