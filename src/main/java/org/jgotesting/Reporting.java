package org.jgotesting;

interface Reporting {
    /**
     * log a message which is only displayed if the test fails
     */
    void log(Object... args);

    /**
     * formats its arguments analogous to printf
     */
    void logf(String format, Object... args);
}
