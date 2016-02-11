package jgotesting.core.examples;

import jgotesting.core.testing.JGoTesting;
import jgotesting.core.testing.T;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JGoTesting.class)
public class TExamples {
    @Test
    public void succeeds(T t) {
        // nothing happens, all is good
    }

    @Test
    public void wrongShape() {
        // should fail validation
    }
}
