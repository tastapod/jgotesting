package examples;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.jgotesting.Checker;
import org.jgotesting.rule.JGoTestRule;
import org.junit.Rule;
import org.junit.Test;

import static org.jgotesting.Assert.*;
import static org.jgotesting.Check.*;
import static org.jgotesting.Testing.*;

public class ExampleUsingRule {
    @Rule
    public JGoTestRule test = new JGoTestRule();

    @Test
    public void succeeds() throws Exception {
    }

    @Test
    public void failsUsingRule() throws Exception {
        test.fail();
    }

    @Test
    public void failsTwiceUsingRule() throws Exception {
        test.fail("once");
        test.fail("twice");
    }

    @Test
    public void failsUsingStaticMethods() throws Exception {
        log("Just shooting the breeze");
        fail("Something went wrong");
        terminate();
    }

    @Test
    public void thisUsesMixOfStaticAndParameterMethods() throws Exception {
        log("First message is just logging");
        test.fail("Second message is an error");
    }

    @Test
    public void failsPartwayThrough() throws Exception {
        test.fail("partway");
        test.terminate("this is the end");

        // shouldn't get here
        test.fail("Shouldn'test get here");
    }

    @Test
    public void capturesException() throws Exception {
        test.log("Hello");
        throw new RuntimeException("oops");
    }

    @Test
    public void handlesMultipleStaticAsserts() throws Exception {
        assertTrue("oops", false);

        assertNotNull(null);

        assertEquals(1, 2);

        assertEquals(3, 2);
    }

    @Test
    public void handlesMultipleStaticChecks() throws Exception {
        checkTrue("oops", false);

        checkNotNull(null);

        checkEquals(1, 2);

        checkEquals(3, 2);
    }

    private class Person {
        private final String name;
        private final int age;
        private final int height;

        Person(String name, int age, int height) {
            this.name = name;
            this.age = age;
            this.height = height;
        }
    }

    @Test
    public void usesChainedChecks() throws Exception {
        Person bob = new Person("Bob", 25, 185);
        Person kate = new Person("Kate", 5, 100);

        Checker over18 = new Checker<Person>() {
            @Override
            public boolean check(Person person) {
                return false;
            }
        };

        test.log(bob.name)
                .check("age", bob.age >= 18)
                .check("height", bob.height, heightOver(180))
                .checkNot("age again", bob, over18);

        test.log(kate.name)
                .checkNot("age", kate, over18)
                .terminateUnless("height", kate.height, heightOver(100));
    }

    private Matcher<Integer> heightOver(final int minHeight) {
        return new BaseMatcher<Integer>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("height greater than " + minHeight);
            }

            @Override
            public boolean matches(Object item) {
                if (item instanceof Integer) {
                    int height = (Integer) item;
                    return height > minHeight;
                }
                return false;
            }
        };
    }
}
