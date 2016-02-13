package jgotesting.core.testing;

public interface Assertions {
    void assertTrue(String message, boolean condition);
    void assertTrue(boolean condition);
    void assertFalse(String message, boolean condition);
    void assertFalse(boolean condition);
    void fail(String message);
    void fail();
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
