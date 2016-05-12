package org.jgotesting;

import org.hamcrest.Matcher;

/**
 * Documenting interface for Hamcrest failures
 */
interface HamcrestFailing {

    /**
     * Mark as failed if we match, and keep going
     */
    <V> void failWhen(V value, Matcher<? super V> matcher);

    /**
     * Mark as failed with a reason if we match, and keep going
     */
    <V> void failWhen(String reason, V value, Matcher<? super V> matcher);

    /**
     * Mark as failed if we don't match, and keep going
     */
    <V> void failUnless(V value, Matcher<? super V> matcher);

    /**
     * Mark as failed with a reason if we don't match, and keep going
     */
    <V> void failUnless(String reason, V value, Matcher<? super V> matcher);

    /**
     * Terminate if we match
     */
    <V> void terminateWhen(V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminate with a reason if we match
     */
    <V> void terminateWhen(String reason, V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminate if we don't match
     */
    <V> void terminateUnless(V value, Matcher<? super V> matcher) throws Exception;

    /**
     * Terminute with a reason if we don't match
     */
    <V> void terminateUnless(String reason, V value, Matcher<? super V> matcher) throws Exception;
}
