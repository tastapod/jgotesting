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

    public static void terminate(Object... args) throws Exception {
        t().terminate(args);
    }

    public static void failfNow(String fmt, Object... args) throws Exception {
        t().terminatef(fmt, args);
    }

    public static void _addFailure(Throwable cause) {
        t().addFailure(cause);
    }

    private static T t() {
        return T.get();
    }

    // Hamcrest matcher methods

    public static <V> void logWhen(String reason, V value, Matcher<? super V> matcher) {
        t().logWhen(reason, value, matcher);
    }

    public static <V> void logWhen(V value, Matcher<? super V> matcher) {
        t().logWhen(value, matcher);
    }

    public static <V> void logUnless(String reason, V value, Matcher<? super V> matcher) {
        t().logUnless(reason, value, matcher);
    }

    public static <V> void logUnless(V value, Matcher<? super V> matcher) {
        t().logUnless(value, matcher);
    }

    public static <V> void failWhen(String reason, V value, Matcher<? super V> matcher) {
        t().failWhen(reason, value, matcher);
    }

    public static <V> void failWhen(V value, Matcher<? super V> matcher) {
        t().failWhen(value, matcher);
    }

    public static <V> void failUnless(String reason, V value, Matcher<? super V> matcher) {
        t().failUnless(reason, value, matcher);
    }

    public static <V> void failUnless(V value, Matcher<? super V> matcher) {
        t().failUnless(value, matcher);
    }

    public static <V> void terminateWhen(String reason, V value, Matcher<? super V> matcher) throws Exception {
        t().terminateWhen(reason, value, matcher);
    }

    public static <V> void terminateWhen(V value, Matcher<? super V> matcher) throws Exception {
        t().terminateWhen(value, matcher);
    }

    public static <V> void terminateUnless(String reason, V value, Matcher<? super V> matcher) throws Exception {
        t().terminateUnless(reason, value, matcher);
    }

    public static <V> void terminateUnless(V value, Matcher<? super V> matcher) throws Exception {
        t().terminateUnless(value, matcher);
    }
}
