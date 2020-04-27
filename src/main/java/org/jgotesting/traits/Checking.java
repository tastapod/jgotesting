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
package org.jgotesting.traits;

import org.hamcrest.Matcher;
import org.jgotesting.Checker;

/**
 * Documenting interface for non-fatal failures
 */
public interface Checking<T extends Checking> {

    /**
     * Fail with a description if check is false, and keep going anyway
     */
    T check(String description, boolean check);

    /**
     * Fail if check is false, and keep going anyway
     */
    T check(boolean check);

    /**
     * Fail with a description if check is false, and keep going anyway
     */
    <V> T check(String description, V value, Checker<? super V> checker);

    /**
     * Fail if check is false, and keep going anyway
     */
    <V> T check(V value, Checker<? super V> checker);

    /**
     * Fail with a description if we don't match, and keep going anyway
     */
    <V> T check(String description, V value, Matcher<? super V> matcher);

    /**
     * Fail if we don't match, and keep going anyway
     */
    <V> T check(V value, Matcher<? super V> matcher);

    /**
     * Fail with a description if check is true, and keep going anyway
     */
    T checkNot(String description, boolean check);

    /**
     * Fail if check is true, and keep going anyway
     */
    T checkNot(boolean check);

    /**
     * Fail with a description if check is true, and keep going anyway
     */
    <V> T checkNot(String description, V value, Checker<? super V> checker);

    /**
     * Fail if check is true, and keep going anyway
     */
    <V> T checkNot(V value, Checker<? super V> checker);

    /**
     * Fail with a description if we match, and keep going anyway
     */
    <V> T checkNot(String description, V value, Matcher<? super V> matcher);

    /**
     * Fail if we match, and keep going anyway
     */
    <V> T checkNot(V value, Matcher<? super V> matcher);
}
