package nl.robinthedev.nats.annotation;

import static java.util.stream.Collectors.*;

import jakarta.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

class ConcatenatedSubject {
  private String methodSubject;
  private String classSubject;

  public ConcatenatedSubject() {}

  private static boolean isNotBlank(String input) {
    return input != null && !input.isBlank();
  }

  public ConcatenatedSubject methodSubject(@Nullable String subject) {
    methodSubject = subject;
    return this;
  }

  public ConcatenatedSubject classSubject(@Nullable String subject) {
    classSubject = subject;
    return this;
  }

  public Optional<String> getSubject() {
    return Optional.of(
            Stream.of(classSubject, methodSubject)
                .filter(ConcatenatedSubject::isNotBlank)
                .collect(joining(".")))
        .filter(ConcatenatedSubject::isNotBlank);
  }
}
