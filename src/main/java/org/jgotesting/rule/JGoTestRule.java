package org.jgotesting.rule;

import org.jgotesting.JGoTest;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;


public class JGoTestRule extends JGoTest implements TestRule {
    private static final ThreadLocal<JGoTest> instance = new ThreadLocal<>();

    /**
     * Manage access to the ThreadLocal instance
     */
    public JGoTestRule() {
        instance.set(this);
    }

    protected static JGoTest get() {
        final JGoTest test = instance.get();
        if (test == null) {
            throw new RuntimeException("Add this to your test class:\n\n@Rule\npublic JGoTestRule test = new JGoTestRule();\n\n");
        }
        return test;
    }

    @Override
    protected void finish() throws Exception {
        try {
            super.finish();
        } finally {
            instance.remove();
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
