package nl.robinthedev.nats.subscription;

import java.util.stream.Collectors;
import java.util.stream.Stream;

class ClassSubject {
  private final Subject subject;

  public ClassSubject(Subscriptions subscription) {
    this.subject = Subject.of(subscription.subject());
  }

  public Subject getSubject() {
    return subject;
  }

  public Subject concat(MethodSubject other) {
    return Subject.of(
        Stream.of(getSubject(), other.getSubject())
            .filter(Subject::isNotEmpty)
            .map(Subject::name)
            .collect(Collectors.joining(".")));
  }
}
