package nl.robinthedev.nats.subscription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.ThrowableAssert.*;

import java.util.List;
import nl.robinthedev.nats.subscription.examples.CombinedSubjectExample;
import nl.robinthedev.nats.subscription.examples.GlobalSubjectExample;
import nl.robinthedev.nats.subscription.examples.MethodSubjectExample;
import nl.robinthedev.nats.subscription.examples.MissingPayloadExample;
import nl.robinthedev.nats.subscription.examples.MissingSubjectExample;
import nl.robinthedev.nats.subscription.examples.MissingSubscribeAnnotationExample;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubscriptionTest {

  @Test
  @DisplayName("Only uses subject from method if class has none")
  void methodSubjectOnly() {
    var context = new TestConstructInvocationContext(MethodSubjectExample.class);

    var subscriptions = Subscription.fromContext(context);

    var subjects = toSubjects(subscriptions);
    assertThat(subjects).contains("only.method.subject");
  }

  private List<String> toSubjects(List<Subscription> subscriptions) {
    return subscriptions.stream().map(Subscription::subject).toList();
  }

  @Test
  @DisplayName("Only uses subject from class if method has none")
  void globalSubjectOnly() {
    var context = new TestConstructInvocationContext(GlobalSubjectExample.class);

    var subscriptions = Subscription.fromContext(context);

    var subjects = toSubjects(subscriptions);
    assertThat(subjects).contains("my.global.subject");
  }

  @Test
  @DisplayName("Uses subject from class and method if both are present")
  void combinedSubject() {
    var context = new TestConstructInvocationContext(CombinedSubjectExample.class);

    var subscriptions = Subscription.fromContext(context);

    var subjects = toSubjects(subscriptions);
    assertThat(subjects).contains("my.comb.ined.subject", "my.comb.ined.second.subject");
  }

  @Test
  @DisplayName("Must have at least one @Subscribe annotated method")
  void missingMethodAnnotation() {
    var context = new TestConstructInvocationContext(MissingSubscribeAnnotationExample.class);

    ThrowingCallable onClassCreation = () -> Subscription.fromContext(context);

    assertThatThrownBy(onClassCreation)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(
            """
            Class 'nl.robinthedev.nats.subscription.examples.MissingSubscribeAnnotationExample' is \
            annotated with @Subscriptions but has no methods with @Subscribe, add at least one method with \
            this annotation or remove the @Subscriptions annotation.""");
  }

  @Test
  @DisplayName("Must have one parameter on the annotated method")
  void missingPayload() {
    var context = new TestConstructInvocationContext(MissingPayloadExample.class);

    ThrowingCallable onClassCreation = () -> Subscription.fromContext(context);

    assertThatThrownBy(onClassCreation)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(
            """
            Class 'nl.robinthedev.nats.subscription.examples.MissingPayloadExample' is \
            annotated with @Subscriptions but has a method with @Subscribe without a parameter, \
            add a parameter to this method describing the payload of the message to receive.""");
  }

  @Test
  @DisplayName("Must have all none empty subjects")
  void missingSubject() {
    var context = new TestConstructInvocationContext(MissingSubjectExample.class);

    ThrowingCallable onClassCreation = () -> Subscription.fromContext(context);

    assertThatThrownBy(onClassCreation)
        .isInstanceOf(IllegalStateException.class)
        .hasMessage(
            """
            Class 'nl.robinthedev.nats.subscription.examples.MissingSubjectExample' is \
            configured with a @Subscribe method without a subject and the @Subsription annotation is also \
            missing a subject. Add at least one subject to either so the subscription knows where to subscribe \
            to.""");
  }
}
