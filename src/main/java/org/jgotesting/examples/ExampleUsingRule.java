package org.jgotesting.examples;

import org.jgotesting.rule.TRule;
import org.junit.Rule;
import org.junit.Test;

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
}
