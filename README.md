# JUnit-compatible testing tool inspired by Go's testing package

## Principles

- A failed check shouldn't stop a test, but should be recorded.
- It should be possible to stop a test when a failed check means there is no point going on.
- Exceptions are an expensive and inappropriate way to model errors.

## Approach

- Try to use seams in JUnit where possible
- Write a custom `org.junit.runner.Runner` (maybe extend `BlockJUnit4ClassRunner`)
- Write a custom `org.junit.runners.model.Statement` that injects a T and captures its output

## Todo

- [x] implement T.fail()
- [x] implement other T methods (log, error, etc.)
- [x] report failures at end
- [x] implement T.failNow()
- [x] show multiple errors as test failures
- [x] implement assert, assertEquals, etc. probably in Assert
- [x] have static import versions
- [x] have T.failIf(matcher), T.failUnless(matcher), so t.failNowIf(foo, nullValue());

## Behaviour that needs tests

### JGoTesting

- [ ] checks the `@Test` method is public, non-static, void and takes a single T parameter

### T

- [ ] `fail()`, `error()` etc. register messages and mark as failed
- [ ] `log`, `logf` register messages and don't mark as failed
- [ ] `failNow`, etc. throws an exception
- [ ] Hamcrest matchers work


### InvokeJGoTestingMethod

- [ ] throws an exception if there was a failure


## How to realse to Maven Central

Run `gradle uploadArchives` to deploy to Sonatypes OSS repository. The upload goes to the snapshot repository
if the version ends with "-VERSION" and otherwise to the Maven Central staging repository. Log in to Nexus OSS to
verify the deploy and promote the version to Maven Central.

### Required configuration

Copy `gradle.properties.example` to `~/.gradle/gradle.properties` and enter your details.