package org.jgotesting.traits;

import org.hamcrest.Matcher;
import org.jgotesting.Checker;

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
     * Terminate with a description if check is true
     */
    T terminateWhen(String description, boolean check) throws Exception;

    /**
     * Terminate if check is true
     */
    T terminateWhen(boolean check) throws Exception;

    /**
     * Terminate with a description if check is true
     */
    <V> T terminateWhen(String description, V value, Checker<? super V> checker) throws Exception;

    /**
     * Terminate if check is true
     */
    <V> T terminateWhen(V value, Checker<? super V> checker) throws Exception;

    /**
     * Terminate with a description if we match
     */
    <V> T terminateWhen(String description, V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminate if we match
     */
    <V> T terminateWhen(V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminute with a description if we don't match
     */
    T terminateUnless(String description, boolean check) throws Exception;

    /**
     * Terminate if check fails
     */
    T terminateUnless(boolean check) throws Exception;

    /**
     * Terminute with a description if we don't match
     */
    <V> T terminateUnless(String description, V value, Checker<? super V> checker) throws Exception;

    /**
     * Terminate if check fails
     */
    <V> T terminateUnless(V value, Checker<? super V> checker) throws Exception;

    /**
     * Terminute with a description if check fails
     */
    <V> T terminateUnless(String description, V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminate if we don't match
     */
    <V> T terminateUnless(V value, Matcher<? super V> matcher) throws Exception;
}
