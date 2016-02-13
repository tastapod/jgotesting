package jgotesting.core.testing;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Assert {

    private static InvocationHandler delegateToStaticAssertMethod = new DelegateToStaticMethod(org.junit.Assert.class);
    private static Assertions delegate = (Assertions) Proxy.newProxyInstance(
            Assertions.class.getClassLoader(),
            new Class[] { Assertions.class },
            delegateToStaticAssertMethod);

    /**
     * Protect constructor since it is a static only class
     */
    protected Assert() {
    }

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

    public void fail(String message) {
        delegate.fail(message);
    }

    public void fail() {
        delegate.fail();
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
}
