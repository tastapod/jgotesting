package org.jgotesting.traits;

import org.hamcrest.Matcher;
import org.jgotesting.Checker;

/**
 * Documenting interface for non-fatal failures
 */
public interface Checking<T extends Checking> {

    /**
     * Fail with a description if check is false, and keep going anyway
     */
    T check(String description, boolean check);

    /**
     * Fail if check is false, and keep going anyway
     */
    T check(boolean check);

    /**
     * Fail with a description if check is false, and keep going anyway
     */
    <V> T check(String description, V value, Checker<? super V> checker);

    /**
     * Fail if check is false, and keep going anyway
     */
    <V> T check(V value, Checker<? super V> checker);

    /**
     * Fail with a description if we don't match, and keep going anyway
     */
    <V> T check(String description, V value, Matcher<? super V> matcher);

    /**
     * Fail if we don't match, and keep going anyway
     */
    <V> T check(V value, Matcher<? super V> matcher);

    /**
     * Fail with a description if check is true, and keep going anyway
     */
    T checkNot(String description, boolean check);

    /**
     * Fail if check is true, and keep going anyway
     */
    T checkNot(boolean check);

    /**
     * Fail with a description if check is true, and keep going anyway
     */
    <V> T checkNot(String description, V value, Checker<? super V> checker);

    /**
     * Fail if check is true, and keep going anyway
     */
    <V> T checkNot(V value, Checker<? super V> checker);

    /**
     * Fail with a description if we match, and keep going anyway
     */
    <V> T checkNot(String description, V value, Matcher<? super V> matcher);

    /**
     * Fail if we match, and keep going anyway
     */
    <V> T checkNot(V value, Matcher<? super V> matcher);
}
