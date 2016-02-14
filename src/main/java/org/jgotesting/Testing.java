package org.jgotesting;

import org.hamcrest.Matcher;
import org.junit.runners.model.MultipleFailureException;

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

    public static void failWithException(Throwable t) {
        t().failWithException(t);
    }

    public static void failNow(Object... args) throws MultipleFailureException {
        t().failNow(args);
    }

    public static void failfNow(String fmt, Object... args) throws MultipleFailureException {
        t().failfNow(fmt, args);
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

    public static <V> void failNowIf(String reason, V value, Matcher<? super V> matcher) throws MultipleFailureException {
        T.failNowIf(reason, value, matcher);
    }

    public static <V> void failNowIf(V value, Matcher<? super V> matcher) throws MultipleFailureException {
        T.failNowIf(value, matcher);
    }

    public static <V> void failNowUnless(String reason, V value, Matcher<? super V> matcher) throws MultipleFailureException {
        T.failNowUnless(reason, value, matcher);
    }

    public static <V> void failNowUnless(V value, Matcher<? super V> matcher) throws MultipleFailureException {
        T.failNowUnless(value, matcher);
    }
}
