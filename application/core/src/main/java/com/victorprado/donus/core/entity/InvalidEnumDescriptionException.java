package com.victorprado.donus.core.entity;

public class InvalidEnumDescriptionException extends RuntimeException {

    public InvalidEnumDescriptionException(String message) {
        super("Invalid description passed to customValueOf. Description: " + message);
    }
}
