/*
 * (BSD License: https://opensource.org/licenses/BSD-3-Clause)
 *
 * Copyright (c) 2016-2017, Dan North and contributors
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the
 * following conditions are met:
 *
 * * Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 *
 * * Neither the name of JGoTesting nor the names of
 *   its contributors may be used to endorse or promote products
 *   derived from this software without specific prior written
 *   permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jgotesting;

import org.jgotesting.rule.JGoTestRule;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

/**
 * This is like an integration test for {@link JGoTestRule} in situ as a {@link Rule}
 */
public class JGoTestRuleTest {

    /**
     * Test-friendly {@link JGoTestRule} we can look at and {@link #reset}
     */
    private static class InspectableJGoTestRule extends JGoTestRule {
        boolean failed() {
            return failed;
        }

        List<Throwable> events() {
            return events;
        }

        /**
         * Pretend nothing bad has happened
         */
        void reset() {
            events.clear();
            failed = false;
        }
    }
    @Rule
    public final InspectableJGoTestRule test = new InspectableJGoTestRule();

    @Test
    public void static_assert_fails_test_and_continues_running() throws Exception {
        // before
        org.junit.Assert.assertEquals("No events recorded", true, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test hasn't failed", false, test.failed());

        // fail
        org.jgotesting.Assert.assertTrue("failing assertion", false);

        // after
        org.junit.Assert.assertEquals("Events have been recorded", false, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test has failed", true, test.failed());

        // reset so the rule doesn't fail the test
        test.reset();
    }

    @Test
    public void static_check_fails_test_and_continue_running() throws Exception {
        // before
        org.junit.Assert.assertEquals("No events recorded", true, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test hasn't failed", false, test.failed());

        // fail
        org.jgotesting.Check.checkTrue("failing check", false);

        // after
        org.junit.Assert.assertEquals("Events have been recorded", false, test.events().isEmpty());
        org.junit.Assert.assertEquals("Test has failed", true, test.failed());

        // reset so the rule doesn't fail the test
        test.reset();
    }
}
