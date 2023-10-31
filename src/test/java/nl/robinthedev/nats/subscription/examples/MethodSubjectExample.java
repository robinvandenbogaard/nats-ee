package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.Subscribe;
import nl.robinthedev.nats.subscription.Subscriptions;

/**
 * If the only subject is declared on the method annotation, the method will use that subject only.
 */
@Subscriptions
public class MethodSubjectExample {
  @Subscribe(subject = "only.method.subject")
  public void myMethod(String payload) {}
}
