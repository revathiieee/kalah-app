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
            throw new KalahRuntimeException("Internal Server Error");
        } catch (KalahRuntimeException are) {
            Assert.assertEquals("Internal Server Error", are.getMessage());
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
            throw new KalahRuntimeException("Internal Server Error", new RuntimeException());
        } catch (KalahRuntimeException are) {
            Assert.assertEquals("java.lang.RuntimeException", are.getCause().getClass().getName());
            Assert.assertEquals("Internal Server Error", are.getMessage());
        }
    }
}
