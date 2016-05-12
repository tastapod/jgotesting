package org.jgotesting;

import org.hamcrest.Matcher;

interface HamcrestReporting {
    <V> void logWhen(String reason, V value, Matcher<? super V> matcher);

    <V> void logWhen(V value, Matcher<? super V> matcher);

    <V> void logUnless(String reason, V value, Matcher<? super V> matcher);

    <V> void logUnless(V value, Matcher<? super V> matcher);
}
