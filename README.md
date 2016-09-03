# JUnit-compatible testing tool inspired by Go's testing package

## Principles

- A failed check shouldn't stop a test, but should be recorded.
- It should be possible to stop a test when a failed check means there is no point going on.
- Exceptions are an expensive and inappropriate way to model errors.

## Quick start

1. Add a JGoTesting `@Rule` instance to your test class.

  ```java import org.jgotesting.rule.JGoTestingRule;

  public class MyTest {
      @org.junit.Rule
      public final JGoTestRule test = new JGoTestRule();
  }
  ```

2. Use JGoTesting's static `assertXxx` methods in place of the JUnit ones
   just by replacing an import. Or use the `checkXxx` ones if you prefer.
   All tests in a class with the `@Rule` will be managed by JGoTesting.

  ```java import static org.jgotesting.Assert.*; // same methods as
  org.junit.Assert.* import static org.jgotesting.Check.*; // ditto, with
  different names

  public class MyTest {

      @Rule
      public final JGoTestRule test = new JGoTestRule();

      @Test
      public void checksSeveralThings() {
          // These are all checked, then they all report as failures

          // using assert methods
          assertEqual("this fails", "one", "ONE");
          assertEqual("this also fails", "two", "TWO");

          // same again using check aliases
          checkEqual("so does this", "one", "ONE");
          checkEqual("and this", "two", "TWO");

          // Test fails with four errors. Sweet!
      }
  }
  ```

3. The rule instance is a reference to the current test, so you can
   chain checks together. You can log messages that will only
   be printed if the test fails, using `log` methods. That way you can
   capture narrative about a test without having lots of verbose output
   for passing tests.

  ```java public class MyTest {

      @Rule
      public final JGoTestRule test = new JGoTestRule();

      @Test
      public void checksSeveralThings() {

          test.log("This message only appears if we fail");

          // All these are checked, then they all report as failures
          test
              .check("this fails", "one", equalTo("ONE")) // Hamcrest matcher
              .check("this also fails", "two", equalTo("TWO"))
              .check("so does this", "one".equals("ONE")) // boolean check
              .check("and this", "two".equals("TWO"));

          // Fails with four errors. Sweet!
      }
  }
  ```
4. Sometimes a test fails and there is no point continuing. In that case
   you can terminate the test with a message, or throw an exception like
   you would elsewhere:

  ```java public class MyTest {

      @Rule
      public final JGoTestRule test = new JGoTestRule();

      @Test
      public void terminatesEarly() {
          // ...
          test.terminateIf("unlikely", moon, madeOf("cheese"));

          // We may not get here
          test.terminate("It's no use. I can't go on.");

          // We definitely won't get here
          throw new IllegalStateException("how did we get here?");
      }
  }
  ```

## Worth knowing about

- All the `log`, `check` and `terminate` methods work with either a simple
  boolean expression, a Hamcrest `Matcher<>`, or a `Checker<>`, which is
  a Single Abstract Method (SAM) interface so you can use Java 8 lambdas
  for checking.

- The `log`, `fail` and `terminate` methods have `logf`, `failf` and
  `terminatef` variants that take `printf`-like string formatters.

- The `Testing` class contains static versions of all the `log`, `fail`
  and `terminate` method variants.
