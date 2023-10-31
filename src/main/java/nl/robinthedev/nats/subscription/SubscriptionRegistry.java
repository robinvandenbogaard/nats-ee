package nl.robinthedev.nats.subscription;

interface SubscriptionRegistry {
  void registerSubscriptions(Object instance, SubscriptionsHolder subscriptionsHolder);
}
