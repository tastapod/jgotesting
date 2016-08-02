package org.jgotesting;

import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;

public class TTest {

    private final T t = new T();

    @Test
    public void testFinishesCleanlyIfNothingHappened() throws Exception {
        t.finish();
    }

    @Test
    public void testFailingDoesntThrowException() throws Exception {
        t.fail("Something happened");
    }

    @Test
    public void testFinishThrowsExceptionAfterFailure() throws Exception {
        t.fail("Something happened");
        try {
            t.finish();
        } catch (MultipleFailureException e) {
            e.getFailures();
        }
    }
}
