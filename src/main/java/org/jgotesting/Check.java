/*
 * (BSD License: https://opensource.org/licenses/BSD-3-Clause)
 *
 * Copyright (c) 2016-2017, Dan North and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the
 * following conditions are met:
 *
 * * Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 *
 * * Neither the name of JGoTesting nor the names of
 *   its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
