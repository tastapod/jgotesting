package org.jgotesting;

import org.jgotesting.rule.JGoTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * This is like an integration test for {@link JGoTestRule} in situ as a {@link Rule}
 */
public class JGoTestRuleTest {

    /**
     * Test-friendly {@link JGoTestRule} we can look at and {@link #reset}
     */
    private static class InspectableJGoTestRule extends JGoTestRule {
        boolean failed() {
            return failed;
        }

        List<Throwable> events() {
            return events;
        }

        /**
         * Pretend nothing bad has happened
         */
        void reset() {
            events.clear();
            failed = false;
        }
    }
    @Rule
    public final InspectableJGoTestRule test = new InspectableJGoTestRule();

    @Test
    public void static_assert_fails_test_and_continues_running() throws Exception {
        // before
        org.junit.Assert.assertEquals("No events recorded", true, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test hasn't failed", false, test.failed());

        // fail
        org.jgotesting.Assert.assertTrue("failing assertion", false);

        // after
        org.junit.Assert.assertEquals("Events have been recorded", false, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test has failed", true, test.failed());

        // reset so the rule doesn't fail the test
        test.reset();
    }

    @Test
    public void static_check_fails_test_and_continue_running() throws Exception {
        // before
        org.junit.Assert.assertEquals("No events recorded", true, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test hasn't failed", false, test.failed());

        // fail
        org.jgotesting.Check.checkTrue("failing check", false);

        // after
        org.junit.Assert.assertEquals("Events have been recorded", false, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test has failed", true, test.failed());

        // reset so the rule doesn't fail the test
        test.reset();
    }
}
