package com.victorprado.donus.database.exception;

import com.victorprado.donus.core.usecase.createaccount.DataProviderException;

public class DataProviderInsertException extends DataProviderException {

    private static final String MESSAGE = "Error to execute insert into database";

    public String getMessage() {
        return MESSAGE;
    }
}
