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

import org.jgotesting.events.Failure;
import org.jgotesting.events.LogMessage;
import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class JGoTestTest {
    @Test
    public void finishes_cleanly_when_nothing_happened() throws Exception {
        JGoTest test = new JGoTest();
        test.finish();
    }

    @Test
    public void finishes_cleanly_after_messages_are_logged() throws Exception {
        JGoTest test = new JGoTest();
        test.log("Something happened");
        test.log("Something else happened");
        test.finish();
    }

    @Test
    public void fail_doesnt_immediately_throw_exception() throws Exception {
        JGoTest test = new JGoTest();
        test.fail("Something happened");
    }

    @Test(expected = MultipleFailureException.class)
    public void explodes_at_end_after_failure() throws Exception {
        JGoTest test = new JGoTest();
        test.fail("Something went wrong");
        test.finish();
    }

    @Test
    public void captures_log_messages_along_with_failure() throws Exception {
        JGoTest test = new JGoTest();

        test.log("Something happened");
        test.fail("Something failed");
        try {
            test.finish();
            throw new RuntimeException("Shouldn't get here");
        } catch (MultipleFailureException expected) {
            assertThat(expected, Matchers.containsEvent(new LogMessage("Something happened")));
            assertThat(expected, Matchers.containsEvent(new Failure("Something failed")));
        }
    }

}
