package org.jgotesting;

import org.jgotesting.events.Failure;
import org.jgotesting.events.Message;
import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;

import static org.junit.Assert.assertThat;

public class JGoTestTest {
    @Test
    public void finishes_cleanly_when_nothing_happened() throws Exception {
        JGoTest test = new JGoTest();
        test.finish();
    }

    @Test
    public void finishes_cleanly_after_messages_are_logged() throws Exception {
        JGoTest test = new JGoTest();
        test.log("Something happened");
        test.log("Something else happened");
        test.finish();
    }

    @Test
    public void fail_doesnt_immediately_throw_exception() throws Exception {
        JGoTest test = new JGoTest();
        test.fail("Something happened");
    }

    @Test(expected = MultipleFailureException.class)
    public void explodes_at_end_after_failure() throws Exception {
        JGoTest test = new JGoTest();
        test.fail("Something went wrong");
        test.finish();
    }

    @Test
    public void captures_log_messages_along_with_failure() throws Exception {
        JGoTest test = new JGoTest();

        test.log("Something happened");
        test.fail("Something failed");
        try {
            test.finish();
            throw new RuntimeException("Shouldn't get here");
        } catch (MultipleFailureException expected) {
            assertThat(expected, Matchers.containsEvent(new Message("Something happened")));
            assertThat(expected, Matchers.containsEvent(new Failure("Something failed")));
        }
    }

}
