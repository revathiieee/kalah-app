package com.backbase.kalahapp.exception;

import org.junit.Assert;
import org.junit.Test;

public class KalahGameExceptionTest {

    @Test
    public void testKalahRuntimeException() {
        try {
            throw new KalahRuntimeException();
        } catch (KalahRuntimeException are) {
            Assert.assertNull(are.getMessage());
            Assert.assertNull(are.getCause());
        }
    }

    @Test
    public void testKalahRuntimeExceptionMessage() {
        try {
            throw new KalahRuntimeException("message");
        } catch (KalahRuntimeException are) {
            Assert.assertEquals("message", are.getMessage());
            Assert.assertNull(are.getCause());
        }
    }

    @Test
    public void testKalahRuntimeExceptionSource() {
        try {
            throw new KalahRuntimeException(new RuntimeException());
        } catch (KalahRuntimeException are) {
            Assert.assertEquals("java.lang.RuntimeException", are.getCause().getClass().getName());
        }
    }

    @Test
    public void testKalahRuntimeExceptionMessageSource() {
        try {
            throw new KalahRuntimeException("message", new RuntimeException());
        } catch (KalahRuntimeException are) {
            Assert.assertEquals("java.lang.RuntimeException", are.getCause().getClass().getName());
            Assert.assertEquals("message", are.getMessage());
        }
    }
}
