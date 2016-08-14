package org.jgotesting;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jgotesting.events.Event;
import org.junit.runners.model.MultipleFailureException;

public class Matchers {
    public static Matcher<? super MultipleFailureException> containsEvent(final Event event) {
        return new BaseMatcher<MultipleFailureException>() {
            @Override
            public boolean matches(Object o) {
                if (o instanceof MultipleFailureException) {
                    MultipleFailureException errors = (MultipleFailureException) o;
                    return errors.getFailures().contains(event);
                } else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description
                        .appendText("MultipleFailureException containing ")
                        .appendValue(event);
            }
        };
    }
}
