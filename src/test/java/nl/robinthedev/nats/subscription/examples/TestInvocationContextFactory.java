package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.TestConstructInvocationContext;

public class TestInvocationContextFactory {
  public static TestConstructInvocationContext forMethodSubjectExample() {
    return new TestConstructInvocationContext(MethodSubjectExample.class);
  }

  public static TestConstructInvocationContext forGlobalSubjectExample() {
    return new TestConstructInvocationContext(GlobalSubjectExample.class);
  }

  public static TestConstructInvocationContext forCombinedSubjectExample() {
    return new TestConstructInvocationContext(CombinedSubjectExample.class);
  }

  public static TestConstructInvocationContext forMissingSubscribeAnnotationExample() {
    return new TestConstructInvocationContext(MissingSubscribeAnnotationExample.class);
  }

  public static TestConstructInvocationContext forMissingPayloadExample() {
    return new TestConstructInvocationContext(MissingPayloadExample.class);
  }

  public static TestConstructInvocationContext forMissingSubjectExample() {
    return new TestConstructInvocationContext(MissingSubjectExample.class);
  }
}
