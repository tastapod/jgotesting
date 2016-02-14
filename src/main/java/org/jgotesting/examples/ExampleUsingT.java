package org.jgotesting.examples;

import org.jgotesting.JGoTesting;
import org.jgotesting.T;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.jgotesting.Testing.*;
import static org.jgotesting.Assert.*;

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
        t.error("Something bad happened");
        t.error("Twice!");
    }

    @Test
    public void thisTerminatesPartWayThrough(T t) throws Exception {
        t.error("Something bad happened");
        t.failNow();
        t.error("This never gets reported");
    }

    @Test
    public void thisUsesStaticMethods() throws Exception {
        log("Just shooting the breeze");
        error("Something went wrong");
        failNow();
    }

    @Test
    public void thisUsesMixOfStaticAndParameterMethods(T t) throws Exception {
        log("First message is just logging");
        t.error("Second message is an error");
    }

    @Test
    public void thisUsesAssertions() throws Exception {
        assertTrue("oops", false);

        assertNotNull(null);

        assertEquals(1, 2);

        assertEquals(3, 2);
    }
}
