package nl.robinthedev.nats.subscription.examples;

import nl.robinthedev.nats.subscription.Subscriptions;

/**
 * This will raise an error on startup because no methods have a @{@link
 * nl.robinthedev.nats.subscription.Subscribe} annotation.
 */
@Subscriptions
class MissingSubscribeAnnotationExample {
  void myMethod() {}
}
