package org.jgotesting.examples;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jgotesting.JGoTest;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.jgotesting.Testing.*;

public class ExampleUsingMatchers {
    @Rule
    public JGoTest test = new JGoTest();

    @Test
    public void logsMatchAndFails() throws Exception {
        // logging
        String value = "Some value";
        logWhen(value, notNullValue());

        String result = "Unexpected: the Spanish Inquisition!";
        logUnless(result, startsWith("Expected"));

        // failing
        failUnless(value, nullValue());
        failWhen(result, containsString("Spanish"));

        // check results
        test.failUnless(value, nullValue()); // fail if not null value


        // proposal:
        // JGoTest check, checkNot, and, andNot methods x 4 overloads:

        // Regular
        // check(reason, boolean)

        // Hamcrest
        // check(reason, value, matcher)

        // check(reason?, cheekyMcCheekface)


        terminateWhen(value, notNullValue());

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
