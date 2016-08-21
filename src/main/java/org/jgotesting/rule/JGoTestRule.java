package org.jgotesting.rule;

import org.jgotesting.JGoTest;
import org.jgotesting.Testing;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;

public class JGoTestRule extends JGoTest implements TestRule {

    /**
     * Manage access to the ThreadLocal instance
     */
    public JGoTestRule() {
        Testing.setInstance(this);
    }

    @Override
    protected void finish() throws Exception {
        try {
            super.finish();
        } finally {
            Testing.removeInstance();
        }
    }

    @Override
    public Statement apply(final Statement base, org.junit.runner.Description description) {
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
