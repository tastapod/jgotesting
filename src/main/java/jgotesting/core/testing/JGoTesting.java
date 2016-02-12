package jgotesting.core.testing;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.Arrays;
import java.util.List;

public class JGoTesting extends BlockJUnit4ClassRunner {
    public JGoTesting(Class<?> klass) throws InitializationError {
        super(klass);
    }

    /**
     * Check each <code>@Test</code> method matches:
     *
     * <code>public void blah(T t) { ... }</code>
     *
     * @param errors collecting parameter for validation errors
     */
    @Override
    protected void validateTestMethods(List<Throwable> errors) {
        List<FrameworkMethod> methods = getTestClass().getAnnotatedMethods(Test.class);

        for (FrameworkMethod method : methods) {
            validatePublicVoidTakesSingleT(method, errors);
        }
    }

    /**
     * Add error to list if method:
     * - is static
     * - is not public
     * - is not void
     * - takes anything other than single parameter T
     */
    private void validatePublicVoidTakesSingleT(FrameworkMethod method, List<Throwable> errors) {
        final Class<?>[] singleParameterT = {T.class};

        if (method.isStatic()) {
            errors.add(error(method, "should not be static"));
        }

        if (!method.isPublic()) {
            errors.add(error(method, "should be public"));
        }

        if (!(method.getReturnType()).equals(Void.TYPE)) {
            errors.add(error(method, "should be void"));
        }

        if (!Arrays.equals(method.getMethod().getParameterTypes(), singleParameterT)) {
            errors.add(error(method, "should take a single T parameter"));
        }
    }

    private Throwable error(FrameworkMethod method, String message) {
        return new RuntimeException("Method " + method.getName() + " " + message);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new InvokeJGoTestingMethod(method, test);
    }
}
