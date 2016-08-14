package examples;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jgotesting.rule.JGoTestRule;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.jgotesting.Testing.*;

public class ExampleUsingMatchers {
    @Rule
    public JGoTestRule test = new JGoTestRule();

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
