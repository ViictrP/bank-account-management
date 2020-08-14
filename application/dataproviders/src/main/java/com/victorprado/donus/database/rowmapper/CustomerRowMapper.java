package com.victorprado.donus.database.rowmapper;

import com.victorprado.donus.core.entity.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<Customer> {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String CPF = "cpf";


    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Customer(rs.getString(ID), rs.getString(NAME), rs.getString(CPF));
    }
}
