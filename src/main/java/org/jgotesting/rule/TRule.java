package org.jgotesting.rule;

import org.jgotesting.T;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @pronounced "Troll"
 */
public class TRule extends T implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    base.evaluate();
                } catch (Throwable oops) {
                    addFailure(oops);
                } finally {
                    finish();
                }
            }
        };
    }
}
