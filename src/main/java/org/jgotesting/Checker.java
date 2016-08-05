package org.jgotesting;

/**
 * Single Abstract Method (SAM) interface which can be implemented as a lambda in Java 8
 */
public interface Checker<T> {
    boolean check(T value);
}
