package org.jgotesting;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jgotesting.events.Event;
import org.junit.runners.model.MultipleFailureException;

public class Matchers {
    public static Matcher<? super MultipleFailureException> containsEvent(final Event expected) {
        return new BaseMatcher<MultipleFailureException>() {
            @Override
            public boolean matches(Object errors) {
                if (!(errors instanceof MultipleFailureException)) {
                    return false;
                }

                for (Throwable event : ((MultipleFailureException) errors).getFailures()) {
                    if (foundEvent(event)) {
                        return true;
                    }
                }
                return false;
            }

            private boolean foundEvent(Throwable event) {
                return expected.getClass().isInstance(event)
                        && (
                        (expected.getMessage() == null && event.getMessage() == null)
                                || expected.getMessage().equals(event.getMessage()));
            }

            @Override
            public void describeTo(Description description) {
                description
                        .appendText("MultipleFailureException containing ")
                        .appendText(String.format("%s (%s)",
                                expected.getClass().getName(),
                                expected.getMessage()));
            }
        };
    }
}
