package org.jgotesting;

import org.jgotesting.events.Failure;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    interface JUnitAssertions {
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

    /**
     * invokes static method with the same name and parameters as a method
     * invoked on a dynamic proxy.
     *
     * <p>The method intercepts an {@link AssertionError} and
     * posts a {@link Failure JGoTesting error}
     * instead of unrolling the stack. This allows multiple assertions
     * within the same test.</p>
     */
    static class DelegateToStaticMethod implements InvocationHandler {
        private final Map<MethodSignature, Method> staticMethods;

        /** used to look up method by name and parameters */
        static class MethodSignature {
            private final String name;
            private final Class<?>[] parameterTypes;

            public MethodSignature(Method method) {
                this.name = method.getName();
                this.parameterTypes = method.getParameterTypes();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                MethodSignature that = (MethodSignature) o;

                if (!name.equals(that.name)) return false;
                // Probably incorrect - comparing Object[] arrays with Arrays.equals
                return Arrays.equals(parameterTypes, that.parameterTypes);

            }

            @Override
            public int hashCode() {
                int result = name.hashCode();
                result = 31 * result + Arrays.hashCode(parameterTypes);
                return result;
            }
        }

        public DelegateToStaticMethod(Class target) {
            staticMethods = buildPublicStaticMethodMap(target);
        }

        private Map<MethodSignature, Method> buildPublicStaticMethodMap(Class<?> target) {
            Map<MethodSignature, Method> result = new HashMap<MethodSignature, Method>();

            for (Method method : target.getDeclaredMethods()) {
                // method has name, modifiers, parameters
                int modifiers = method.getModifiers();
                if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                    MethodSignature signature = new MethodSignature(method);
                    result.put(signature, method);
                }
            }
            return result;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method staticMethod = staticMethods.get(new MethodSignature(method));
            try {
                return staticMethod.invoke(null, args);
            } catch (InvocationTargetException e) {
                final Throwable cause = e.getCause();
                if (cause instanceof AssertionError) {
                    Testing.addFailure(cause);
                    return null;
                } else {
                   throw cause;
                }
            }
        }
    }

}
