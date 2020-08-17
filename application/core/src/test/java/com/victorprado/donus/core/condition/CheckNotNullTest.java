package com.victorprado.donus.core.condition;

import org.junit.Test;

public class CheckNotNullTest {

    @Test(expected = NullObjectException.class)
    public void check() {
        CheckNotNull.check((Object) null, "");
    }

    @Test
    public void shouldNotThrowError() {
        CheckNotNull.check(new Object(), "");
    }
}
