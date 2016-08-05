package org.jgotesting.traits;

/**
 * Documenting interface for failures.
 */
public interface Failing<T extends Failing> {
    /**
     * marks the function as having failed but continues execution
     */
    T fail(Object... args);

    /**
     * equivalent to logf followed by fail
     */
    T failf(String fmt, Object... args);
}
