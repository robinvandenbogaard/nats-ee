package nl.robinthedev.nats.subscription;

import io.nats.client.Connection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nl.robinthedev.nats.mapper.NatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
class SubscriptionRegistryImpl implements SubscriptionRegistry {

  private static final Logger log = LoggerFactory.getLogger(SubscriptionRegistryImpl.class);

  private final Connection connection;
  private final NatsMapper mapper;

  @Inject
  SubscriptionRegistryImpl(Connection connection, NatsMapper mapper) {
    this.connection = connection;
    this.mapper = mapper;
  }

  @Override
  public void registerSubscriptions(Object instance, SubscriptionsHolder subscriptionsHolder) {
    subscriptionsHolder
        .getSubscriptions()
        .forEach(subscription -> subscribe(instance, subscription));
  }

  private void subscribe(Object instance, Subscription subscription) {
    var handler = new SubscriptionMessageHandler(mapper, subscription.payloadMethod(), instance);
    var dispatcher = connection.createDispatcher(handler::handle);
    var subject = subscription.subject();
    dispatcher.subscribe(subject);

    var type = subscription.payloadMethod().type();
    log.trace(
        "Added subscription on subject '{}' with payload type '{}'.", subject, type.getName());
  }
}
