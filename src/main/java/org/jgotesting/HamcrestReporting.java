package org.jgotesting;

import org.hamcrest.Matcher;

/**
 * Documenting interface for Hamcrest logging
 */
interface HamcrestReporting {
    /**
     * Log if we match
     */
    <V> void logWhen(V value, Matcher<? super V> matcher);

    /**
     * Log with a reason if we match
     */
    <V> void logWhen(String reason, V value, Matcher<? super V> matcher);

    /**
     * Log if we don't match
     */
    <V> void logUnless(V value, Matcher<? super V> matcher);

    /**
     * Log with a reason if we don't match
     */
    <V> void logUnless(String reason, V value, Matcher<? super V> matcher);
}
