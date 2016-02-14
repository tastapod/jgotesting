package org.jgotesting.examples;

import org.jgotesting.JGoTesting;
import org.jgotesting.T;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.jgotesting.Assert.*;
import static org.jgotesting.Testing.*;

@RunWith(JGoTesting.class)
public class ExampleUsingT {
    @Test
    public void succeedsWithoutParam() {
    }

    @Test
    @SuppressWarnings("UnusedParameters")
    public void succeedsWithParam(T t) {
    }

    @Test
    public void thisFails(T t) throws Exception {
        t.fail();
        t.fail();
    }

    @Test
    public void thisFailsWithMessages(T t) throws Exception {
        t.fail("Something bad happened");
        t.fail("Twice!");
    }

    @Test
    public void thisTerminatesPartWayThrough(T t) throws Exception {
        t.fail("Something bad happened");
        t.failNow();
        t.fail("This never gets reported");
    }

    @Test
    public void thisUsesStaticMethods() throws Exception {
        log("Just shooting the breeze");
        fail("Something went wrong");
        failNow();
    }

    @Test
    public void thisUsesMixOfStaticAndParameterMethods(T t) throws Exception {
        log("First message is just logging");
        t.fail("Second message is an error");
    }

    /**
     * Note: IntelliJ intercepts JUnit assert-style output so it can
     *       do things like diffing <code>assertEquals</code> values,
     *       so it doesn't show the output from <code>assertTrue</code>
     *       or <code>assertNotNull</code>, but these still work correctly.
     */
    @Test
    public void thisUsesAssertions() throws Exception {
        assertTrue("oops", false);

        assertNotNull(null);

        assertEquals(1, 2);

        assertEquals(3, 2);
    }
}
