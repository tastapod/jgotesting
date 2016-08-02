package org.jgotesting.examples;

import org.jgotesting.rule.TRule;
import org.junit.Rule;
import org.junit.Test;

import static org.jgotesting.Assert.*;
import static org.jgotesting.Check.*;
import static org.jgotesting.Testing.*;

public class ExampleUsingRule {
    @Rule
    public TRule t = new TRule();

    @Test
    public void succeeds() throws Exception {
    }

    @Test
    public void failsUsingRule() throws Exception {
        t.fail();
    }

    @Test
    public void failsTwiceUsingRule() throws Exception {
        t.fail("once");
        t.fail("twice");
    }

    @Test
    public void failsUsingStaticMethods() throws Exception {
        log("Just shooting the breeze");
        fail("Something went wrong");
        terminate();
    }

    @Test
    public void thisUsesMixOfStaticAndParameterMethods() throws Exception {
        log("First message is just logging");
        t.fail("Second message is an error");
    }

    @Test
    public void failsPartwayThrough() throws Exception {
        t.fail("partway");
        t.terminate("this is the end");

        // shouldn't get here
        t.fail("Shouldn't get here");
    }

    @Test
    public void capturesException() throws Exception {
        t.log("Hello");
        throw new RuntimeException("oops");
    }

    @Test
    public void handlesMultipleStaticAsserts() throws Exception {
        assertTrue("oops", false);

        assertNotNull(null);

        assertEquals(1, 2);

        assertEquals(3, 2);
    }

    @Test
    public void handlesMultipleStaticChecks() throws Exception {
        checkTrue("oops", false);

        checkNotNull(null);

        checkEquals(1, 2);

        checkEquals(3, 2);
    }
}
