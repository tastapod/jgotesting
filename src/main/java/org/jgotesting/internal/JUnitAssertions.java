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
package org.jgotesting.internal;

/**
 * tracks the signatures of the static assert methods in {@link org.junit.Assert}
 *
 * This allows us to have a dynamic proxy that delegates to the appropriate static method.
 */
public interface JUnitAssertions {
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
