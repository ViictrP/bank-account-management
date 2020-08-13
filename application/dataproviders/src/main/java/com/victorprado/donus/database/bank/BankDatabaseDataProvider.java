package com.victorprado.donus.database.bank;

import com.victorprado.donus.core.entity.BankAccount;
import com.victorprado.donus.core.entity.Customer;
import com.victorprado.donus.core.usecase.createaccount.DataProviderException;
import com.victorprado.donus.core.usecase.createaccount.ManageBankAccount;
import com.victorprado.donus.core.usecase.createaccount.ManageCustomer;
import com.victorprado.donus.database.exception.DataProviderInsertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import java.util.Optional;

public class BankDatabaseDataProvider implements ManageCustomer, ManageBankAccount {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankDatabaseDataProvider.class);

    private final JdbcTemplate jdbcTemplate;

    public BankDatabaseDataProvider(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> getOne(String cpf) {
        try {
            Customer customerEntity = jdbcTemplate.queryForObject("SELECT c.* FROM donus.customer c WHERE c.cpf = ?", Customer.class, cpf);
            return Optional.ofNullable(customerEntity);
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public boolean register(@NonNull Customer customer) {
        try {
            int result = jdbcTemplate.update("INSERT INTO donus.customer(id, cpf, name) VALUES(?,?,?)", customer.getId(), customer.getCpf(), customer.getName());
            return Boolean.parseBoolean(String.valueOf(result));
        } catch (DataAccessException error) {
            LOGGER.error(error.getMessage());
            throw new DataProviderInsertException();
        }
    }

    @Override
    public boolean create(BankAccount account) throws DataProviderException {
        return false;
    }
}
