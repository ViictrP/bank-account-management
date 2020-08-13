package com.victorprado.donus.core.condition;

public final class CheckNotNull {

    private CheckNotNull() {

    }

    public static void check(Object object, String message) {
        if (object == null) {
            throw new NullObjectException(message);
        }
    }
}
