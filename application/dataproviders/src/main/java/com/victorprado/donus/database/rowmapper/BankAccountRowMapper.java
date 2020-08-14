package com.victorprado.donus.database.rowmapper;

import com.victorprado.donus.core.entity.BankAccount;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BankAccountRowMapper implements RowMapper<BankAccount> {

    private static final String ID = "id";
    private static final String BALANCE = "balance";
    private static final String NUMBER = "number";
    private static final String CREATED_DATE = "created_date";
    private static final String DELETED = "deleted";

    @Override
    public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(rs.getString(ID));
        bankAccount.setBalance(rs.getDouble(BALANCE));
        bankAccount.setNumber(rs.getString(NUMBER));
        bankAccount.setCreatedDate(LocalDateTime.parse(rs.getString(CREATED_DATE)));
        bankAccount.setDeleted(rs.getBoolean(DELETED));
        return bankAccount;
    }
}
