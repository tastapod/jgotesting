package org.jgotesting;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

public class InvokeJGoTestingMethod extends Statement {
    private final FrameworkMethod method;
    private final Object target;

    public InvokeJGoTestingMethod(FrameworkMethod method, Object target) {
        this.method = method;
        this.target = target;
    }

    @Override
    public void evaluate() throws Throwable {
        try {
            final T t = T.create();

            if (methodTakesParameter()) {
                method.invokeExplosively(target, t);
            } else {
                method.invokeExplosively(target);
            }

            // if we get here the method completed without throwing an exception
            if (t.failed()) {
                throw new MultipleFailureException(t.getErrors());
            }
        } finally {
            T.destroy();
        }
    }

    private boolean methodTakesParameter() {
        return method.getMethod().getParameterTypes().length == 1;
    }
}
