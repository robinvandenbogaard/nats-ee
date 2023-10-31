package nl.robinthedev.nats.subscription;

interface SubscribeMethod {
  Subject subject();

  PayloadMethod messageMethod();

  boolean isSubjectEmpty();

  boolean hasRequiredParameter();
}
