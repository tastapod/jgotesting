package org.jgotesting;

import org.jgotesting.internal.DelegateToStaticMethod;
import org.jgotesting.internal.JUnitAssertions;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * tracks the signatures of the static assert methods in {@link org.junit.Assert}
 * with "check" instead of "assert".
 */
@SuppressWarnings("unused")
public class Check {
    /** Protect constructor since it is a static only class */
    protected Check() {
    }

    // checkBlah methods generated from delegate, and then made static
    // and then s/static void assert/static void check/

    public static void checkEquals(double expected, double actual) {
        delegate.assertEquals(expected, actual);
    }

    public static void checkNotEquals(String message, double first, double second, double delta) {
        delegate.assertNotEquals(message, first, second, delta);
    }

    public static void checkTrue(String message, boolean condition) {
        delegate.assertTrue(message, condition);
    }

    public static void checkEquals(String message, double expected, double actual) {
        delegate.assertEquals(message, expected, actual);
    }

    public static void checkNull(String message, Object object) {
        delegate.assertNull(message, object);
    }

    public static void checkNotSame(Object unexpected, Object actual) {
        delegate.assertNotSame(unexpected, actual);
    }

    public static void checkNotEquals(Object first, Object second) {
        delegate.assertNotEquals(first, second);
    }

    public static void checkFalse(String message, boolean condition) {
        delegate.assertFalse(message, condition);
    }

    public static void checkNull(Object object) {
        delegate.assertNull(object);
    }

    public static void checkNotEquals(long first, long second) {
        delegate.assertNotEquals(first, second);
    }

    public static void checkNotEquals(double first, double second, double delta) {
        delegate.assertNotEquals(first, second, delta);
    }

    public static void checkFalse(boolean condition) {
        delegate.assertFalse(condition);
    }

    public static void checkNotNull(Object object) {
        delegate.assertNotNull(object);
    }

    public static void checkEquals(String message, double expected, double actual, double delta) {
        delegate.assertEquals(message, expected, actual, delta);
    }

    public static void checkEquals(double expected, double actual, double delta) {
        delegate.assertEquals(expected, actual, delta);
    }

    public static void checkNotEquals(String message, Object first, Object second) {
        delegate.assertNotEquals(message, first, second);
    }

    public static void checkNotNull(String message, Object object) {
        delegate.assertNotNull(message, object);
    }

    public static void checkSame(Object expected, Object actual) {
        delegate.assertSame(expected, actual);
    }

    public static void checkEquals(String message, Object expected, Object actual) {
        delegate.assertEquals(message, expected, actual);
    }

    public static void checkNotSame(String message, Object unexpected, Object actual) {
        delegate.assertNotSame(message, unexpected, actual);
    }

    public static void checkEquals(Object expected, Object actual) {
        delegate.assertEquals(expected, actual);
    }

    public static void checkNotEquals(String message, long first, long second) {
        delegate.assertNotEquals(message, first, second);
    }

    public static void checkTrue(boolean condition) {
        delegate.assertTrue(condition);
    }

    public static void checkSame(String message, Object expected, Object actual) {
        delegate.assertSame(message, expected, actual);
    }

    public static void checkEquals(long expected, long actual) {
        delegate.assertEquals(expected, actual);
    }

    public static void checkEquals(String message, float expected, float actual, float delta) {
        delegate.assertEquals(message, expected, actual, delta);
    }

    public static void checkEquals(float expected, float actual, float delta) {
        delegate.assertEquals(expected, actual, delta);
    }

    public static void checkEquals(String message, long expected, long actual) {
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
            new DelegateToStaticMethod(org.junit.Assert.class) {
                @Override
                protected DelegateToStaticMethod.MethodSignature buildMethodSignature(Method method) {
                    return new MethodSignature(method).withName(method.getName().replace("assert", "check"));
                }
            });
}
