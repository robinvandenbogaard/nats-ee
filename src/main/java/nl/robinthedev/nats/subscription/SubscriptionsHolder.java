package nl.robinthedev.nats.subscription;

import java.util.List;

class SubscriptionsHolder {
  private final List<Subscription> subscriptions;

  public SubscriptionsHolder(List<Subscription> subscriptions) {
    this.subscriptions = subscriptions;
  }

  public List<Subscription> getSubscriptions() {
    return subscriptions;
  }
}
