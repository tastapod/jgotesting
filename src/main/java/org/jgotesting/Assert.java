package org.jgotesting;

import org.jgotesting.internal.DelegateToStaticMethod;

import java.lang.reflect.Proxy;

/**
 * tracks the signatures of the static assert methods in {@link org.junit.Assert}
 */
@SuppressWarnings("unused")
public class Assert {
    /** Protect constructor since it is a static only class */
    protected Assert() {
    }

    // assertBlah methods generated from delegate, and then made static

    public static void assertEquals(double expected, double actual) {
        delegate.assertEquals(expected, actual);
    }

    public static void assertNotEquals(String message, double first, double second, double delta) {
        delegate.assertNotEquals(message, first, second, delta);
    }

    public static void assertTrue(String message, boolean condition) {
        delegate.assertTrue(message, condition);
    }

    public static void assertEquals(String message, double expected, double actual) {
        delegate.assertEquals(message, expected, actual);
    }

    public static void assertNull(String message, Object object) {
        delegate.assertNull(message, object);
    }

    public static void assertNotSame(Object unexpected, Object actual) {
        delegate.assertNotSame(unexpected, actual);
    }

    public static void assertNotEquals(Object first, Object second) {
        delegate.assertNotEquals(first, second);
    }

    public static void assertFalse(String message, boolean condition) {
        delegate.assertFalse(message, condition);
    }

    public static void assertNull(Object object) {
        delegate.assertNull(object);
    }

    public static void assertNotEquals(long first, long second) {
        delegate.assertNotEquals(first, second);
    }

    public static void assertNotEquals(double first, double second, double delta) {
        delegate.assertNotEquals(first, second, delta);
    }

    public static void assertFalse(boolean condition) {
        delegate.assertFalse(condition);
    }

    public static void assertNotNull(Object object) {
        delegate.assertNotNull(object);
    }

    public static void assertEquals(String message, double expected, double actual, double delta) {
        delegate.assertEquals(message, expected, actual, delta);
    }

    public static void assertEquals(double expected, double actual, double delta) {
        delegate.assertEquals(expected, actual, delta);
    }

    public static void assertNotEquals(String message, Object first, Object second) {
        delegate.assertNotEquals(message, first, second);
    }

    public static void assertNotNull(String message, Object object) {
        delegate.assertNotNull(message, object);
    }

    public static void assertSame(Object expected, Object actual) {
        delegate.assertSame(expected, actual);
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        delegate.assertEquals(message, expected, actual);
    }

    public static void assertNotSame(String message, Object unexpected, Object actual) {
        delegate.assertNotSame(message, unexpected, actual);
    }

    public static void assertEquals(Object expected, Object actual) {
        delegate.assertEquals(expected, actual);
    }

    public static void assertNotEquals(String message, long first, long second) {
        delegate.assertNotEquals(message, first, second);
    }

    public static void assertTrue(boolean condition) {
        delegate.assertTrue(condition);
    }

    public static void assertSame(String message, Object expected, Object actual) {
        delegate.assertSame(message, expected, actual);
    }

    public static void assertEquals(long expected, long actual) {
        delegate.assertEquals(expected, actual);
    }

    public static void assertEquals(String message, float expected, float actual, float delta) {
        delegate.assertEquals(message, expected, actual, delta);
    }

    public static void assertEquals(float expected, float actual, float delta) {
        delegate.assertEquals(expected, actual, delta);
    }

    public static void assertEquals(String message, long expected, long actual) {
        delegate.assertEquals(message, expected, actual);
    }

    // Here's how it works

    /**
     * Dynamic proxy to call static methods on {@link org.junit.Assert}
     * and intercept {@link AssertionError}
     */
    private static JUnitAssertions delegate = (JUnitAssertions) Proxy.newProxyInstance(
            JUnitAssertions.class.getClassLoader(),
            new Class[] { JUnitAssertions.class },
            new DelegateToStaticMethod(org.junit.Assert.class));

    /**
     * tracks the signatures of the static assert methods in {@link org.junit.Assert}
     */
    private interface JUnitAssertions {
        void assertTrue(String message, boolean condition);
        void assertTrue(boolean condition);
        void assertFalse(String message, boolean condition);
        void assertFalse(boolean condition);
        void assertEquals(String message, Object expected, Object actual);
        void assertEquals(Object expected, Object actual);
        void assertNotEquals(String message, Object first, Object second);
        void assertNotEquals(Object first, Object second);
        void assertNotEquals(String message, long first, long second);
        void assertNotEquals(long first, long second);
        void assertNotEquals(String message, double first, double second, double delta);
        void assertNotEquals(double first, double second, double delta);
        void assertEquals(String message, double expected, double actual, double delta);
        void assertEquals(String message, float expected, float actual, float delta);
        void assertEquals(long expected, long actual);
        void assertEquals(String message, long expected, long actual);
        void assertEquals(double expected, double actual);
        void assertEquals(String message, double expected, double actual);
        void assertEquals(double expected, double actual, double delta);
        void assertEquals(float expected, float actual, float delta);
        void assertNotNull(String message, Object object);
        void assertNotNull(Object object);
        void assertNull(String message, Object object);
        void assertNull(Object object);
        void assertSame(String message, Object expected, Object actual);
        void assertSame(Object expected, Object actual);
        void assertNotSame(String message, Object unexpected, Object actual);
        void assertNotSame(Object unexpected, Object actual);
    }

}
