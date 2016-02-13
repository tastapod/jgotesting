package org.jgotesting;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class InvokeJGoTestingMethod extends Statement {
    private final FrameworkMethod method;
    private final Object target;
    private final T t = new T();

    public InvokeJGoTestingMethod(FrameworkMethod method, Object target) {
        this.method = method;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        Testing.setInstance(t);

        if (methodTakesParameter()) {
            method.invokeExplosively(target, t);
        } else {
            method.invokeExplosively(target);
        }

        // if we get here the method completed without throwing an exception
        if (t.failed()) {
            throw new MultipleFailureException(t.getErrors());
        }
    }

    private boolean methodTakesParameter() {
        return method.getMethod().getParameterTypes().length == 1;
    }
}
