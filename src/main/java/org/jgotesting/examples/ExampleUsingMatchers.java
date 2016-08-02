package org.jgotesting.examples;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jgotesting.rule.TRule;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.jgotesting.Testing.*;

public class ExampleUsingMatchers {
    @Rule
    public TRule t = new TRule();

    @Test
    public void logsMatchAndFails() throws Exception {
        // logging
        logWhen("This isn't null", notNullValue());
        logUnless("Unexpected result", startsWith("Expected"));

        // failing
        failWhen("Contains bad words", containsString("bad"));
        failUnless("Not null", nullValue());

        terminateWhen("Terminates here", notNullValue());

        fail("We never get here");
    }

    @Test
    public void hasCustomMatcher() throws Exception {
        failWhen(100, belowMinimumHeight(130));
        failUnless(180, belowMinimumHeight(130));
    }

    private Matcher<Integer> belowMinimumHeight(final int minHeight) {
        return new BaseMatcher<Integer>() {
            @Override
            public boolean matches(Object item) {
                if (item instanceof Integer) {
                    Integer height = (Integer) item;
                    return height < minHeight;
                } else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("height below " + minHeight + "cm");
            }
        };
    }
}
