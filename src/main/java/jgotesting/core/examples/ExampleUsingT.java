package jgotesting.core.examples;

import jgotesting.core.testing.JGoTesting;
import jgotesting.core.testing.T;
import org.junit.Test;
import org.junit.runner.RunWith;

import static jgotesting.core.testing.Assert.*;
import static jgotesting.core.testing.Testing.*;

@RunWith(JGoTesting.class)
public class ExampleUsingT {
    @Test
    public void succeeds(T t) {
        // nothing happens, all is good
    }

    @Test
    public void thisFails(T t) throws Exception {
        t.fail();
    }

    @Test
    public void thisErrors(T t) throws Exception {
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
    public void usesStaticVersion() throws Exception {
        log("Just shooting the breeze");
        error("Something went wrong");
        failNow();
    }

    @Test
    public void usesMixOfStaticAndParameterBecauseWeird(T t) throws Exception {
        log("First message is just logging");
        t.error("Second message is an error");
    }

    @Test
    public void usesAssertions() throws Exception {
        assertTrue("oops, 1 should equal 2", false);
        assertTrue("and 3 should equal 4", false);
        assertEquals(3, 2);
        assertNotNull(null); // default error message
    }
}
