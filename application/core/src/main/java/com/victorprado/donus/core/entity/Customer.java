package com.victorprado.donus.core.entity;

import com.victorprado.donus.core.usecase.createaccount.InvalidEntityException;
import com.victorprado.donus.core.validator.EntityValidator;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

public class Customer extends Upgradable implements EntityValidator {

    private String id;
    private String name;
    private String cpf;

    public Customer() {
    }

    public Customer(String name, String cpf) {
        this.name = name;
        this.cpf = cpf;
    }

    public Customer(String id, String name, String cpf) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public void validate() {
        if (StringUtils.isEmpty(cpf) || StringUtils.isEmpty(name)) {
            throw new InvalidEntityException();
        }
    }

    @Override
    void updateLastMofiedDate() {
        this.createdDate = LocalDateTime.now();
    }

    @Override
    void generateCreatedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
