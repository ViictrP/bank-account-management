package com.victorprado.donus.core.exception;

public abstract class CoreException extends RuntimeException {

    public CoreException(String message) {
        super(message);
    }

    public CoreException() {
        super();
    }
}
