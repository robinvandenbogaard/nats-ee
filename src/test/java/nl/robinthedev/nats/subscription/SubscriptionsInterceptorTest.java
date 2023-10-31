package nl.robinthedev.nats.subscription;

import static org.assertj.core.api.Assertions.*;

import nl.robinthedev.nats.subscription.examples.MethodSubjectExample;
import nl.robinthedev.nats.subscription.examples.NotASubscriptionsHolderExample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Given the creation of a class")
class SubscriptionsInterceptorTest {

  private SubscriptionsInterceptor interceptor;
  private CountingSubscriptionHolders subscriptionRegistry;

  @BeforeEach
  void setUp() {
    subscriptionRegistry = new CountingSubscriptionHolders();
    interceptor = new SubscriptionsInterceptor(subscriptionRegistry);
  }

  @Nested
  @DisplayName("without @Subscriptions annotation")
  public class NonSubscriptionHolderClassesTests {

    @Test
    @DisplayName("should not be adding subscriptions")
    void should_add_a_subscription_for_each_annotated_method() {
      var targetClazz = NotASubscriptionsHolderExample.class;
      var invocationContext = new TestConstructInvocationContext(targetClazz);

      interceptor.subscribeOnConstruct(invocationContext);

      assertThat(subscriptionRegistry.getSubscriptionHoldersAdded()).isZero();
    }

    @Test
    @DisplayName("should not instantiate the class")
    void should_not_instantiate_the_class() {
      var targetClazz = NotASubscriptionsHolderExample.class;
      var invocationContext = new TestConstructInvocationContext(targetClazz);

      interceptor.subscribeOnConstruct(invocationContext);

      assertThat(invocationContext.isProceedInvoked()).isFalse();
    }
  }

  @Nested
  @DisplayName("with @Subscriptions annotation")
  public class SubscriptionHolderClasses {

    @Test
    @DisplayName("should be registered at the registry")
    void usesMethodSubject() {
      var targetClazz = MethodSubjectExample.class;
      var invocationContext = new TestConstructInvocationContext(targetClazz);

      interceptor.subscribeOnConstruct(invocationContext);

      assertThat(subscriptionRegistry.getSubscriptionHoldersAdded()).isEqualTo(1);
    }

    @Test
    @DisplayName("should instantiate the class")
    void should_instantiate_the_class() {
      var targetClazz = MethodSubjectExample.class;
      var invocationContext = new TestConstructInvocationContext(targetClazz);

      interceptor.subscribeOnConstruct(invocationContext);

      assertThat(invocationContext.isProceedInvoked()).isTrue();
    }
  }
}
