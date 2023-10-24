package nl.robinthedev.nats.config;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.io.IOException;

@ApplicationScoped
class NatsConnectionProducer {

  @Inject Instance<Options> options;

  @Produces
  Connection connection() throws IOException, InterruptedException {
    if (!options.isUnsatisfied()) {
      return Nats.connect(options.get());
    } else {
      return Nats.connect();
    }
  }
}
