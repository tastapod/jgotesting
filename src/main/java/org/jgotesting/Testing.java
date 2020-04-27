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

import org.hamcrest.Matcher;

@SuppressWarnings("unused")
public class Testing {
    private static final ThreadLocal<JGoTest> instance = new ThreadLocal<>();

    public static void log(Object... args) {
        test().log(args);
    }

    public static void logf(String format, Object... args) {
        test().logf(format, args);
    }

    public static void fail(Object... args) {
        test().fail(args);
    }

    public static void failf(String fmt, Object... args) {
        test().failf(fmt, args);
    }

    public static void terminate(Object... args) throws Exception {
        test().terminate(args);
    }

    public static void failfNow(String fmt, Object... args) throws Exception {
        test().terminatef(fmt, args);
    }

    public static void _addFailure(Throwable cause) {
        test().addFailure(cause);
    }

    // Hamcrest matcher methods

    public static <V> void logIf(String reason, V value, Matcher<? super V> matcher) {
        test().logIf(reason, value, matcher);
    }

    public static <V> void logIf(V value, Matcher<? super V> matcher) {
        test().logIf(value, matcher);
    }

    public static <V> void logUnless(String reason, V value, Matcher<? super V> matcher) {
        test().logUnless(reason, value, matcher);
    }

    public static <V> void logUnless(V value, Matcher<? super V> matcher) {
        test().logUnless(value, matcher);
    }

    public static <V> void failIf(String reason, V value, Matcher<? super V> matcher) {
        test().checkNot(reason, value, matcher);
    }

    public static <V> void failIf(V value, Matcher<? super V> matcher) {
        test().checkNot(value, matcher);
    }

    public static <V> void failUnless(String reason, V value, Matcher<? super V> matcher) {
        test().check(reason, value, matcher);
    }

    public static <V> void failUnless(V value, Matcher<? super V> matcher) {
        test().check(value, matcher);
    }

    public static <V> void terminateIf(String reason, V value, Matcher<? super V> matcher) throws Exception {
        test().terminateIf(reason, value, matcher);
    }

    public static <V> void terminateIf(V value, Matcher<? super V> matcher) throws Exception {
        test().terminateIf(value, matcher);
    }

    public static <V> void terminateUnless(String reason, V value, Matcher<? super V> matcher) throws Exception {
        test().terminateUnless(reason, value, matcher);
    }

    public static <V> void terminateUnless(V value, Matcher<? super V> matcher) throws Exception {
        test().terminateUnless(value, matcher);
    }

    // Access to ThreadLocal instance

    public static void setInstance(JGoTest test) {
        instance.set(test);
    }

    public static void removeInstance() {
        instance.remove();
    }

    private static JGoTest test() {
        final JGoTest test = instance.get();
        if (test == null) {
            throw new RuntimeException("Add this to your test class:\n\n@Rule\npublic JGoTestRule test = new JGoTestRule();\n\n");
        }
        return test;
    }
}
