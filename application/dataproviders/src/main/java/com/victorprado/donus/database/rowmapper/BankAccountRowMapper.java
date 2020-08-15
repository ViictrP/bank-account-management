package com.victorprado.donus.database.rowmapper;

import com.victorprado.donus.core.entity.BankAccount;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BankAccountRowMapper implements RowMapper<BankAccount> {

    private static final String ID = "id";
    private static final String BALANCE = "balance";
    private static final String NUMBER = "number";
    private static final String CREATED_DATE = "created_date";
    private static final String LAST_MODIFIED_DATE = "last_modified_date";
    private static final String DELETED = "deleted";

    @Override
    public BankAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(rs.getString(ID));
        bankAccount.setBalance(BigDecimal.valueOf(rs.getDouble(BALANCE)));
        bankAccount.setNumber(rs.getString(NUMBER));

        bankAccount.setCreatedDate(rs.getObject(CREATED_DATE, LocalDateTime.class));
        bankAccount.setLastModifiedDate(rs.getObject(LAST_MODIFIED_DATE, LocalDateTime.class));

        bankAccount.setDeleted(rs.getBoolean(DELETED));
        return bankAccount;
    }
}
