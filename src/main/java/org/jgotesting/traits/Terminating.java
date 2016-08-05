package org.jgotesting.traits;

import org.hamcrest.Matcher;

/**
 * Documenting interface for fatal failures
 */
public interface Terminating<T extends Terminating> {

    /**
     * marks the function as having failed and stops its execution
     */
    void terminate(Object... args) throws Exception;

    /**
     * equivalent to logf followed by terminate
     */
    void terminatef(String fmt, Object... args) throws Exception;

    /**
     * Terminate if we match
     */
    <V> T terminateWhen(V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminate with a description if we match
     */
    <V> T terminateWhen(String description, V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminate if we don't match
     */
    <V> T terminateUnless(V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminute with a description if we don't match
     */
    <V> T terminateUnless(String description, V value, Matcher<? super V> matcher) throws Exception;
}
