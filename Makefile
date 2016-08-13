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

#
# derived values
#
m2_dir := $(LOCAL_M2)/$(subst .,/,$(group))/$(repo)/$(version)
package := $(group):$(project)
project_uri := $(user_org)/$(repo)/$(package)
now := $(shell date -u +"%Y-%m-%d'T'%H:%M:%S")

.DEFAULT_GOAL := build/sync_to_maven.done

build/upload_files.done: $(PUBLISHED_POM)
	for f in $(wildcard $(m2_dir)/*); do \
	    curl --fail \
		-v -X PUT \
		-u "$(bintray_user):$(bintray_key)" \
		-H "X-Bintray-Package: $(package)" \
		-H "X-Bintray-Version: $(version)" \
		--data-binary @"$$f" \
		"$(BINTRAY_API)/content/$(user_org)/$(repo)/$${f#$(LOCAL_M2)/}"; \
	done
	date > $@

build/sign_files.done: build/upload_files.done
	# Signing $(user_org)/$(group):$(project) version $(version)
	curl --fail \
	    -X POST \
	    -u "$(bintray_user):$(bintray_key)" \
	    -H "X-GPG-PASSPHRASE: $(gpg_password)" \
	    -H "X-GPG-SUBJECT: $(gpg_subject)" \
	    "$(BINTRAY_API)/gpg/$(project_uri)/versions/$(version)"
	date > $@

build/sync_to_maven.done: build/sign_files.done
	# Maven syncing $(project_uri) version $(version)

	data=; \
	curl --fail \
	    -X POST \
	    -u "$(bintray_user):$(bintray_key)" \
	    -H "Content-Type: application/json" \
	    -d '{ \
	    "username": "$(call user_prop,ossrhUsername)", \
	    "password": "$(call user_prop,ossrhPassword)" \
	}'"$$data" \
	    "$(BINTRAY_API)/maven_central_sync/$(project_uri)/versions/$(version)"
	date > $@

