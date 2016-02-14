package org.jgotesting.examples;

import org.junit.Test;

import static org.jgotesting.Testing.fail;

public class ExampleWithoutAnnotation {
    @Test
    public void usesJGoTesting() {
        fail(); // tells you to use @RunWith(JGoTesting.class)
    }
}
