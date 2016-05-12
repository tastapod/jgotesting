package org.jgotesting;

/**
 * Documenting interface for failures
 */
interface Failing {
    /**
     * marks the function as having failed but continues execution
     */
    void fail(Object... args);

    /**
     * equivalent to logf followed by fail
     */
    void failf(String fmt, Object... args);

    /**
     * marks the function as having failed and stops its execution
     */
    void terminate(Object... args) throws Exception;

    /**
     * equivalent to logf followed by terminate
     */
    void terminatef(String fmt, Object... args) throws Exception;
}
