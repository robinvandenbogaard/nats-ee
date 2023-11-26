package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.Subscribe;
import nl.robinthedev.nats.subscription.Subscriptions;

/**
 * If the both the class and method annotations have a subject declared, the subject will be
 * combined.
 */
@Subscriptions(subject = "my.comb")
class CombinedSubjectExample {
  @Subscribe(subject = "ined.subject")
  void myMethod(String payload) {}

  @Subscribe(subject = "ined.second.subject")
  void mySecondMethod(String payload) {}
}
