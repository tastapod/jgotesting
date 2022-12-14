package examples;

import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

public class Main {
    public static void main(String[] args) throws Exception {
        Class[] examples = {
                ExampleUsingRule.class,
                ExampleUsingMatchers.class,
                ExampleWithoutRule.class
        };
        final JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        for (Class example : examples) {
            System.out.println("===================================");
            System.out.println("Running " + example);
            junit.run(example);
        }
    }
}
