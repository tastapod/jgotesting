package org.jgotesting.examples;

import org.junit.Test;

import static org.jgotesting.Testing.fail;

public class ExampleWithoutRule {
    @Test
    public void usesJGoTesting() {
        fail(); // tells you to use @Rule
    }
}
