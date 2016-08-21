#
# functions
#
read_prop = $(shell cat "$(2)" |\
    grep '=' |\
    sed 's/^  *//;s/  *$$//;s/  *=  */=/' |\
    awk -F= '$$1 == key {print $$2; exit}' key="$(1)")

settings_prop = $(patsubst '%',%,$(call read_prop,$(1),settings.gradle))

local_prop = $(call read_prop,$(1),gradle.properties)

user_prop = $(call read_prop,$(1),$(HOME)/.gradle/gradle.properties)

#
# constants
#
LOCAL_M2 := $(HOME)/.m2/repository
BINTRAY_API := https://api.bintray.com
PUBLISHED_POM := build/publications/mavenCustom/pom-default.xml

#
# project settings
#
project := $(call settings_prop,rootProject.name)
user_org := $(call local_prop,userOrg)
repo := $(call local_prop,repo)
group := $(call local_prop,group)
version := $(call local_prop,version)

#
# user settings
#
bintray_user := $(call user_prop,bintrayUsername)
bintray_key := $(call user_prop,bintrayApiKey)
gpg_subject := $(call user_prop,gpgSigningSubject)
gpg_password := $(call user_prop,gpgSigningPassword)
ossr_username := $(call user_prop,ossrhUsername)
ossr_password := $(call user_prop,ossrhPassword)

#
# derived values
#
m2_dir := $(LOCAL_M2)/$(subst .,/,$(group))/$(repo)/$(version)
package := $(group):$(project)
project_uri := $(user_org)/$(repo)/$(package)
now := $(shell date -u +"%Y-%m-%d'T'%H:%M:%S")


.DEFAULT_GOAL := sync_to_maven

artifacts := $(wildcard $(m2_dir)/*)
uploaded := $(patsubst %,build/upload_%.done,$(notdir $(artifacts)))

build/upload_%.done: $(m2_dir)/% $(PUBLISHED_POM)
	# Uploading $<
	file="$(subst $(LOCAL_M2)/,,$<)" && \
	curl --fail \
	    -X PUT \
	    -u "$(bintray_user):$(bintray_key)" \
	    -H "X-Bintray-Package: $(package)" \
	    -H "X-Bintray-Version: $(version)" \
	    -H "X-Bintray-Override: 1" \
	    --data-binary @"$<" \
	    "$(BINTRAY_API)/content/$(user_org)/$(repo)/$$file" && \
	date > "$@"

.PHONY: upload_files
upload_files: $(uploaded)


build/sign_files.done: $(uploaded)
	# Signing $(user_org)/$(group):$(project) version $(version)
	curl --fail \
	    -X POST \
	    -u "$(bintray_user):$(bintray_key)" \
	    -H "X-GPG-PASSPHRASE: $(gpg_password)" \
	    -H "X-GPG-SUBJECT: $(gpg_subject)" \
	    "$(BINTRAY_API)/gpg/$(project_uri)/versions/$(version)" && \
	date > $@

.PHONY: sign_files
sign_files: build/sign_files.done


build/sync_to_maven.done: build/sign_files.done
	# Maven syncing $(project_uri) version $(version)
	curl --fail \
	    -X POST \
	    -u "$(bintray_user):$(bintray_key)" \
	    -H "Content-Type: application/json" \
	    -d '{ \
	    "username": "$(ossr_username)", \
	    "password": "$(ossr_password)" \
	    }' \
	    "$(BINTRAY_API)/maven_central_sync/$(project_uri)/versions/$(version)" && \
	date > $@

.PHONY: sync_to_maven
sync_to_maven: build/sync_to_maven.done

