#!/bin/bash

PROPS="$HOME/.gradle/gradle.properties" 

read_prop() {
    awk -F= '$1 == key {print $2}' key="$1" "$PROPS"
}

bintray_user="$(read_prop 'bintrayUsername')"
bintray_key="$(read_prop 'bintrayApiKey')"

gpg_password="$(read_prop 'gpgSigningPassword')"

echo "$body" | curl -X POST \
    -u"$bintray_user:$bintray_key" \
    -H "X-GPG-PASSPHRASE: $gpg_password" \
    -H "X-GPG-SUBJECT: tastapod" \
    "https://api.bintray.com/gpg/jgotesting/jgotesting/org.jgotesting:jgotesting/versions/0.7"
