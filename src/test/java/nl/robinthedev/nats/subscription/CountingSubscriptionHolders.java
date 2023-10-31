package nl.robinthedev.nats.subscription;

class CountingSubscriptionHolders implements SubscriptionRegistry {
  private int holdersAdded;

  public int getSubscriptionHoldersAdded() {
    return holdersAdded;
  }

  @Override
  public void registerSubscriptions(Object instance, SubscriptionsHolder subscriptionsHolder) {
    holdersAdded++;
  }
}
