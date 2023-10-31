package nl.robinthedev.nats.subscription;

import jakarta.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

interface Subscription {
  static List<Subscription> fromContext(InvocationContext context) {
    var declaringClazz = context.getConstructor().getDeclaringClass();
    var subscriptionMethods = subscribeMethods(declaringClazz);

    if (subscriptionMethods.isEmpty()) {
      throw new IllegalStateException(
          "Class '%s' is annotated with @Subscriptions but has no methods with @Subscribe, add at least one method with this annotation or remove the @Subscriptions annotation."
              .formatted(declaringClazz.getName()));
    }

    if (hasMissingPayloadParameter(subscriptionMethods)) {
      throw new IllegalStateException(
          "Class '%s' is annotated with @Subscriptions but has a method with @Subscribe without a parameter, add a parameter to this method describing the payload of the message to receive."
              .formatted(declaringClazz.getName()));
    }

    if (hasAnyEmptySubjects(subscriptionMethods)) {
      throw new IllegalStateException(
          "Class '%s' is configured with a @Subscribe method without a subject and the @Subsription annotation is also missing a subject. Add at least one subject to either so the subscription knows where to subscribe to."
              .formatted(declaringClazz.getName()));
    }

    return subscriptionMethods.stream().map(Subscription::toSubscription).toList();
  }

  static Subscription toSubscription(SubscribeMethod subscribeMethod) {
    return new SubscriptionImpl(subscribeMethod.subject().name(), subscribeMethod.messageMethod());
  }

  static boolean hasAnyEmptySubjects(List<? extends SubscribeMethod> subscribeMethods) {
    return subscribeMethods.stream().anyMatch(SubscribeMethod::isSubjectEmpty);
  }

  static boolean hasMissingPayloadParameter(List<? extends SubscribeMethod> subscribeMethods) {
    return !subscribeMethods.stream().allMatch(SubscribeMethod::hasRequiredParameter);
  }

  static List<? extends SubscribeMethod> subscribeMethods(Class<?> declaringClazz) {
    return getSubscribeMethodStream(declaringClazz)
        .map(method -> new PayloadSubscribeMethod(declaringClazz, method))
        .toList();
  }

  static Stream<Method> getSubscribeMethodStream(Class<?> declaringClazz) {
    return Arrays.stream(declaringClazz.getDeclaredMethods())
        .filter(method -> method.isAnnotationPresent(Subscribe.class));
  }

  String subject();

  PayloadMethod payloadMethod();
}
