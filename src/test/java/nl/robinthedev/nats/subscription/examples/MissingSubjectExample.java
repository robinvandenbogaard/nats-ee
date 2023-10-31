package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.Subscribe;
import nl.robinthedev.nats.subscription.Subscriptions;

/**
 * This will raise an error on startup because the class and method both are missing the subject in
 * their annotations.
 */
@Subscriptions
public class MissingSubjectExample {

  @Subscribe
  public void myMethod(String payload) {}
}
