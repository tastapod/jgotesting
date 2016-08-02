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
- [x] implement T.terminate()
- [x] show multiple errors as test failures
- [x] implement assert, assertEquals, etc. probably in Assert
- [x] have static import versions
- [x] have T.failWhen(matcher), T.failUnless(matcher), etc., so t.terminateWhen(foo, nullValue());
- [ ] make it work with JMock
- [ ] make it work with Mockito

## Behaviour that needs tests

### JGoTesting

- [x] checks the `@Test` method is public, non-static, void and takes a single T parameter

### T

- [ ] `fail()`, `error()` etc. register messages and mark as failed
- [ ] `log`, `logf` register messages and don't mark as failed
- [ ] `fail`, `terminate`, etc. throws an exception
- [ ] Hamcrest matchers work


### InvokeJGoTestingMethod

- [ ] throws an exception if there was a failure


## How to realse to Maven Central

1. Run `gradle uploadArchives` to deploy to Sonatypes OSS repository. The upload goes to the
snapshot repository if the version ends with "-VERSION" and otherwise to the Maven Central
staging repository.
2. Log in to Nexus OSS to verify the staged artifacts and release them
using [these instructions](http://central.sonatype.org/pages/releasing-the-deployment.html).

### Required configuration

Copy `gradle.properties.example` to `~/.gradle/gradle.properties` and enter your details.
