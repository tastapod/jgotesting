package org.jgotesting;

import org.junit.Test;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.Arrays;
import java.util.List;

/**
 * {@link org.junit.runner.Runner} that allows a test to continue
 * after a failure, so a single test can contain multiple checks.
 *
 * See {@link org.jgotesting.examples.ExampleUsingT ExampleUsingT}
 * for some examples.
 */
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
        for (FrameworkMethod method : getTestClass().getAnnotatedMethods(Test.class)) {
            validateMethodSignature(method, errors);
        }
    }

    /**
     * Add error to list if method:
     * - is static
     * - is not public
     * - is not void
     * - takes anything other than single parameter T
     */
    private void validateMethodSignature(FrameworkMethod method, List<Throwable> errors) {
        final List<Class<?>> singleParameterT = Arrays.asList(new Class<?>[]{T.class});
        final List<Class<?>> noParameters = Arrays.asList(new Class<?>[]{});
        final List<Class<?>> params = Arrays.asList(method.getMethod().getParameterTypes());

        if (method.isStatic()) addError(method, "should not be static", errors);

        if (!method.isPublic()) addError(method, "should be public", errors);

        if (!(method.getReturnType()).equals(Void.TYPE)) addError(method, "should be void", errors);

        if (!(singleParameterT.equals(params) || noParameters.equals(params))) {
            addError(method, "should take no params or a single T parameter", errors);
        }
    }

    private void addError(FrameworkMethod method, String message, List<Throwable> errors) {
        errors.add(new RuntimeException("Method " + method.getName() + " " + message));
    }



    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new InvokeJGoTestingMethod(method, test);
    }
}
