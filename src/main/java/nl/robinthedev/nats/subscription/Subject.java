package nl.robinthedev.nats.subscription;

import java.util.Objects;

record Subject(String name) {

  public static final Subject EMPTY = Subject.of("");

  Subject {
    Objects.requireNonNull(name);
  }

  public static Subject of(String name) {
    return new Subject(name);
  }

  public boolean isEmpty() {
    return EMPTY.equals(this);
  }

  public boolean isNotEmpty() {
    return !isEmpty();
  }
}
