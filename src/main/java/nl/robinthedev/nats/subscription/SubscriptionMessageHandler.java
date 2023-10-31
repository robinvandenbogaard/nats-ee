package nl.robinthedev.nats.subscription;

import io.nats.client.Message;
import java.lang.reflect.InvocationTargetException;
import nl.robinthedev.nats.mapper.NatsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SubscriptionMessageHandler {

  private static final Logger log = LoggerFactory.getLogger(SubscriptionMessageHandler.class);

  private final NatsMapper mapper;
  private final PayloadMethod method;
  private final Object instance;

  public SubscriptionMessageHandler(NatsMapper mapper, PayloadMethod method, Object instance) {
    this.mapper = mapper;
    this.method = method;
    this.instance = instance;
  }

  public void handle(Message message) {
    try {
      method.invoke(instance, mapper.deserialize(message.getData(), method.type()));
    } catch (IllegalAccessException | InvocationTargetException e) {
      log.error("Error while invoking subscription handler '{}'.", method.name(), e);
      throw new RuntimeException(e);
    }
  }
}
