package org.jgotesting;

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

    static T t() {
        return T.get();
    }
}
