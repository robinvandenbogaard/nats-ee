package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.Subscribe;
import nl.robinthedev.nats.subscription.Subscriptions;

/** This will raise an error on startup because no methods have a @Subscribe annotation. */
@Subscriptions(subject = "my.comb")
public class CombinedSubjectExample {
  @Subscribe(subject = "ined.subject")
  void myMethod(String payload) {}

  @Subscribe(subject = "ined.second.subject")
  void mySecondMethod(String payload) {}
}
