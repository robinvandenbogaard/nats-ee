package nl.robinthedev.nats.subscription;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundConstruct;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Subscriptions
@Priority(Integer.MAX_VALUE)
@Interceptor
class SubscriptionsInterceptor {

  private static final Logger log = LoggerFactory.getLogger(SubscriptionsInterceptor.class);

  private final SubscriptionRegistry registry;

  @Inject
  SubscriptionsInterceptor(SubscriptionRegistry registry) {
    this.registry = registry;
  }

  @AroundConstruct
  void subscribeOnConstruct(InvocationContext context) {
    var targetClazz = context.getConstructor().getDeclaringClass();
    if (targetClazz.isAnnotationPresent(Subscriptions.class)) {
      instantiate(context);
      var instance = context.getTarget();
      registry.registerSubscriptions(
          instance, new SubscriptionsHolder(Subscription.fromContext(context)));
    }
  }

  private void instantiate(InvocationContext context) {
    try {
      context.proceed();
    } catch (Exception e) {
      log.error(
          "Error while instantiation bean {}. It required to instantiate this bean to register the nats message handler.",
          context.getConstructor().getDeclaringClass().getName(),
          e);
      throw new RuntimeException(e);
    }
  }
}
