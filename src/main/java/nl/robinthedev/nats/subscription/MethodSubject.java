package nl.robinthedev.nats.subscription;

class MethodSubject {
  private final Subject subject;

  public MethodSubject(Subscribe subscribe) {
    this.subject = Subject.of(subscribe.subject());
  }

  public Subject getSubject() {
    return subject;
  }
}
