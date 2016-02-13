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

[x] implement T.fail()
[x] implement other T methods (log, error, etc.)
[x] report failures at end
[x] implement T.failNow()
[x] show multiple errors as test failures
[ ] implement assert, assertEquals, etc. probably in T.Assert
[ ] have static import versions
[ ] have T.failIf(matcher), T.failIf(lambda), e.g. t.failIf(isNull(foo));

## Behaviour that needs tests

### JGoTesting

- checks the `@Test` method is public, non-static, void and takes a single T parameter

### T

- `fail()`, `error()` etc. register messages and mark as failed
- `log`, `logf` register messages and don't mark as failed
- `fatal`, `failNow`, etc. throw an exception


### InvokeJGoTestingMethod

- throws exception if there was a failure
