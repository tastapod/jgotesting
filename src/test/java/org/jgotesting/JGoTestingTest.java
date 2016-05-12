package org.jgotesting;

import org.junit.Test;
import org.junit.runners.model.InitializationError;

@SuppressWarnings("WeakerAccess")
public class JGoTestingTest {

    public static class HasPrivateTestMethod {
        @Test
        private void shouldBePublicMethod() {
        }
    }

    @Test(expected = InitializationError.class)
    public void doesntInitialiseForPrivateTestMethod() throws Exception {
        new JGoTesting(HasPrivateTestMethod.class);
    }


    public static class HasStaticTestMethod {
        @Test
        public static void shouldBeInstanceMethod() {
        }
    }

    @Test(expected = InitializationError.class)
    public void doesntInitialiseForStaticTestMethod() throws Exception {
        new JGoTesting(HasStaticTestMethod.class);
    }


    public static class HasTestMethodWithoutParameters {
        @Test
        public void hasNoParameters() {
        }
    }

    @Test
    public void initialisesForMethodWithoutParameters() throws Exception {
        new JGoTesting(HasTestMethodWithoutParameters.class);
    }


    public static class HasTestMethodWithSingleTParameter {
        @Test
        public void hasSingleTParameter(T t) {
        }
    }

    @Test
    public void initialisesForMethodWithSingleTParameter() throws Exception {
        new JGoTesting(HasTestMethodWithSingleTParameter.class);
    }


    public static class HasTestMethodWithWrongParameterType {
        @Test
        public void hasWrongParameterType(String notT) {
        }
    }

    @Test(expected = InitializationError.class)
    public void doesntInitialiseForMethodWithWrongParameterType() throws Exception {
        new JGoTesting(HasTestMethodWithWrongParameterType.class);
    }


    public static class HasNonTestMethod {
        @Test
        public void thisIsFine() {
        }

        public void thisIsIgnored() {
        }
    }

    @Test
    public void ignoresNonTestMethod() throws Exception {
        new JGoTesting(HasNonTestMethod.class);
    }

}