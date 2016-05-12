package org.jgotesting;

/**
 * Documenting interface for logging messages
 */
interface Reporting { // "Logging" is already a loaded interface name in Java
    /**
     * log a message which is only displayed if the test fails
     */
    void log(Object... args);

    /**
     * formats its arguments analogous to printf
     */
    void logf(String format, Object... args);
}
