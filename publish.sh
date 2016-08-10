#!/bin/bash

BINTRAY_API='https://api.bintray.com'

# Read a value from a properties file
function read_prop {
    cat "$2" |\
        grep '=' |\
        sed 's/^  *//;s/  *$//;s/  *=  */=/' |\
        awk -F= '$1 == key {print $2; exit}' key="$1"
}

function settings_prop {
    # Strip surrounding '' from settings.gradle values
    read_prop "$1" settings.gradle | sed "s/^['\"]//;s/['\"]//"
}

function local_prop {
    read_prop "$1" gradle.properties
}

function user_prop {
    read_prop "$1" "$HOME/.gradle/gradle.properties"
}

project="$(settings_prop 'rootProject.name')"

user_org="$(user_prop 'userOrg')"
bintray_user="$(user_prop 'bintrayUsername')"
bintray_key="$(user_prop 'bintrayApiKey')"
gpg_password="$(user_prop 'gpgSigningPassword')"

maven_username="$(user_prop 'ossrhUsername')"
maven_password="$(user_prop 'ossrhPassword')"

maven_data="{
    "username": "$maven_username",
    "password": "$maven_password"
}"

repo="$(local_prop 'repo')"
group="$(local_prop 'group')"
version="$(local_prop 'version')"

echo "Signing $group:$project version $version"

curl -X POST \
    -u"$bintray_user:$bintray_key" \
    -H "X-GPG-PASSPHRASE: $gpg_password" \
    -H "X-GPG-SUBJECT: tastapod" \
    "https://api.bintray.com/gpg/$user_org/$repo/$group:$project/versions/$version"

curl -X POST \
    -u"$bintray_user:$bintray_key" \
    -d"$maven_data" "maven_central_sync/$user_org/$project/$group:$project/versions/$version"
