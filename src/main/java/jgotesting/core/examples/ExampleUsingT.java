package jgotesting.core.examples;

import jgotesting.core.testing.JGoTesting;
import jgotesting.core.testing.T;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JGoTesting.class)
public class ExampleUsingT {
    @Test
    public void succeeds(T t) {
        // nothing happens, all is good
    }

    @Test
    public void fails(T t) throws Exception {
        t.fail();
        t.failNow();
        System.out.println("Hello");
        t.fail();
    }
}
