package org.jgotesting;

import org.hamcrest.Matcher;

@SuppressWarnings("unused")
public class Testing {

    public static void log(Object... args) {
        t().log(args);
    }

    public static void logf(String format, Object... args) {
        t().logf(format, args);
    }

    public static void fail(Object... args) {
        t().fail(args);
    }

    public static void failf(String fmt, Object... args) {
        t().failf(fmt, args);
    }

    public static void failNow(Object... args) throws Exception {
        t().failNow(args);
    }

    public static void failfNow(String fmt, Object... args) throws Exception {
        t().failfNow(fmt, args);
    }

    static void addFailure(Throwable cause) {
        t().addFailure(cause);
    }

    private static T t() {
        return T.get();
    }

    // Hamcrest matcher methods

    public static <V> void logIf(String reason, V value, Matcher<? super V> matcher) {
        T.logIf(reason, value, matcher);
    }

    public static <V> void logIf(V value, Matcher<? super V> matcher) {
        T.logIf(value, matcher);
    }

    public static <V> void logUnless(String reason, V value, Matcher<? super V> matcher) {
        T.logUnless(reason, value, matcher);
    }

    public static <V> void logUnless(V value, Matcher<? super V> matcher) {
        T.logUnless(value, matcher);
    }

    public static <V> void failIf(String reason, V value, Matcher<? super V> matcher) {
        T.failIf(reason, value, matcher);
    }

    public static <V> void failIf(V value, Matcher<? super V> matcher) {
        T.failIf(value, matcher);
    }

    public static <V> void failUnless(String reason, V value, Matcher<? super V> matcher) {
        T.failUnless(reason, value, matcher);
    }

    public static <V> void failUnless(V value, Matcher<? super V> matcher) {
        T.failUnless(value, matcher);
    }

    public static <V> void failNowIf(String reason, V value, Matcher<? super V> matcher) throws Exception {
        T.failNowIf(reason, value, matcher);
    }

    public static <V> void failNowIf(V value, Matcher<? super V> matcher) throws Exception {
        T.failNowIf(value, matcher);
    }

    public static <V> void failNowUnless(String reason, V value, Matcher<? super V> matcher) throws Exception {
        T.failNowUnless(reason, value, matcher);
    }

    public static <V> void failNowUnless(V value, Matcher<? super V> matcher) throws Exception {
        T.failNowUnless(value, matcher);
    }
}
