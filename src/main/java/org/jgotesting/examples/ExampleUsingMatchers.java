package org.jgotesting.examples;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jgotesting.JGoTesting;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.jgotesting.Testing.*;

@RunWith(JGoTesting.class)
public class ExampleUsingMatchers {
    @Test
    public void logsMatchAndFails() throws Exception {
        // logging
        logIf("This isn't null", notNullValue());
        logUnless("Unexpected result", startsWith("Expected"));

        // failing
        failIf("Contains bad words", containsString("bad"));
        failUnless("Not null", nullValue());

        failNowIf("Terminates here", notNullValue());

        fail("We never get here");
    }

    @Test
    public void hasCustomMatcher() throws Exception {
        failIf(100, belowMinimumHeight(130));
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
