package org.jgotesting;

import org.hamcrest.Matcher;

interface HamcrestFailing {
    <V> void failWhen(String reason, V value, Matcher<? super V> matcher);

    <V> void failWhen(V value, Matcher<? super V> matcher);

    <V> void failUnless(String reason, V value, Matcher<? super V> matcher);

    <V> void failUnless(V value, Matcher<? super V> matcher);

    <V> void terminateWhen(String reason, V value, Matcher<? super V> matcher) throws Exception;

    <V> void terminateWhen(V value, Matcher<? super V> matcher) throws Exception;

    <V> void terminateUnless(String reason, V value, Matcher<? super V> matcher) throws Exception;

    <V> void terminateUnless(V value, Matcher<? super V> matcher) throws Exception;
}
