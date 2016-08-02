package org.jgotesting.examples;

import org.jgotesting.T;
import org.jgotesting.rule.TRule;
import org.junit.Rule;
import org.junit.Test;

import static org.jgotesting.Assert.assertEquals;
import static org.jgotesting.Assert.assertNotNull;
import static org.jgotesting.Assert.assertTrue;

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
}
