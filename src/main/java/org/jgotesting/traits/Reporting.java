package org.jgotesting.traits;

import org.hamcrest.Matcher;

/**
 * Documenting interface for logging messages
 */
public interface Reporting<T extends Reporting> { // "Logging" is already a loaded name in Java
    /**
     * log a message which is only displayed if the test fails
     */
    T log(Object... args);

    /**
     * formats its arguments analogous to printf
     */
    T logf(String format, Object... args);

    /**
     * Log if we match
     */
    <V> T logIf(V value, Matcher<? super V> matcher);

    /**
     * Log with a reason if we match
     */
    <V> T logIf(String reason, V value, Matcher<? super V> matcher);

    /**
     * Log if we don't match
     */
    <V> T logUnless(V value, Matcher<? super V> matcher);

    /**
     * Log with a reason if we don't match
     */
    <V> T logUnless(String reason, V value, Matcher<? super V> matcher);
}
