package org.jgotesting.examples;

import org.jgotesting.JGoTest;
import org.junit.Rule;
import org.junit.Test;

import static org.jgotesting.Assert.*;
import static org.jgotesting.Check.*;
import static org.jgotesting.Testing.*;

public class ExampleUsingRule {
    @Rule
    public JGoTest test = new JGoTest();

    @Test
    public void succeeds() throws Exception {
    }

    @Test
    public void failsUsingRule() throws Exception {
        test.fail();
    }

    @Test
    public void failsTwiceUsingRule() throws Exception {
        test.fail("once");
        test.fail("twice");
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
        test.fail("Second message is an error");
    }

    @Test
    public void failsPartwayThrough() throws Exception {
        test.fail("partway");
        test.terminate("this is the end");

        // shouldn'test get here
        test.fail("Shouldn'test get here");
    }

    @Test
    public void capturesException() throws Exception {
        test.log("Hello");
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
