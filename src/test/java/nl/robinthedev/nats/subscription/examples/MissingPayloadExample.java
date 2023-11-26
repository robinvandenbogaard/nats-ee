package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.Subscribe;
import nl.robinthedev.nats.subscription.Subscriptions;

/**
 * This will raise an error on startup because the annotated method is missing a payload parameter.
 * The method must have 1 parameter.
 */
@Subscriptions
class MissingPayloadExample {

  @Subscribe(subject = "example")
  void myMethod() {}
}
