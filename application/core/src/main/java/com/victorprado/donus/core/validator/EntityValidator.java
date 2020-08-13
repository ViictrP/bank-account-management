package com.victorprado.donus.core.validator;

import com.victorprado.donus.core.usecase.createaccount.InvalidEntityException;

public interface EntityValidator {

    void validate() throws InvalidEntityException;
}
