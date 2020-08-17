package com.victorprado.donus.core.condition;

import org.junit.Test;

import java.util.Optional;

public class CheckNullTest {

    @Test(expected = NotNullObjectException.class)
    public void checkOptional() {
        CheckNull.check(Optional.of(new Object()), "");
    }

    @Test(expected = NotNullObjectException.class)
    public void check() {
        CheckNull.check(new Object(), "");
    }

    @Test
    public void shouldNotThrowError() {
        CheckNull.check((Object) null, "");
    }

    @Test
    public void shouldNotThrowErrorWithOptional() {
        CheckNull.check(Optional.empty(), "");
    }
}
