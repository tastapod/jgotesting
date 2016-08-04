package org.jgotesting;

import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;

public class JGoTestTest {

    private final JGoTest test = new JGoTest();

    @Test
    public void testFinishesCleanlyIfNothingHappened() throws Exception {
        test.finish();
    }

    @Test
    public void testFailingDoesntThrowException() throws Exception {
        test.fail("Something happened");
    }

    @Test
    public void testFinishThrowsExceptionAfterFailure() throws Exception {
        test.fail("Something happened");
        try {
            test.finish();
        } catch (MultipleFailureException e) {
            e.getFailures();
        }
    }
}
