package com.victorprado.donus.core.condition;

import java.util.Optional;

public final class CheckNull {

    private CheckNull() {

    }

    public static void check(Object object, String message) {
        if (object != null) {
            throw new NotNullObject(message);
        }
    }

    public static void check(Optional object, String message) {
        if (object.isPresent()) {
            throw new NotNullObject(message);
        }
    }
}
