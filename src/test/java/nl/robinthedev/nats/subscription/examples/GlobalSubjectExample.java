package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.Subscribe;
import nl.robinthedev.nats.subscription.Subscriptions;

/**
 * If the only subject is declared on the class annotation, the method will use that subject only.
 */
@Subscriptions(subject = "my.global.subject")
class GlobalSubjectExample {
  @Subscribe
  void myMethod(String payload) {}
}
