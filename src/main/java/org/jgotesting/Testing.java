package org.jgotesting;

import org.junit.runners.model.MultipleFailureException;

public class Testing {

    public static void error(Object... args) {
        t().error(args);
    }

    public static void errorf(String fmt, Object... args) {
        t().errorf(fmt, args);
    }

    public static void fail() {
        t().fail();
    }

    public static void failNow() throws MultipleFailureException {
        t().failNow();
    }

    public static void fatal(Object... args) throws MultipleFailureException {
        t().fatal(args);
    }

    public static void fatalf(String fmt, Object... args) throws MultipleFailureException {
        t().fatalf(fmt, args);
    }

    public static void log(Object... args) {
        t().log(args);
    }

    public static void logf(String format, Object... args) {
        t().logf(format, args);
    }

    static T t() {
        return T.get();
    }

    public static void failWithException(Throwable t) {
        t().failWithException(t);
    }
}
