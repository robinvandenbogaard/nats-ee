package nl.robinthedev.nats.annotation;

record PublishParameters(String subject, byte[] body) {
  public String bodyString() {
    return new String(body);
  }
}
