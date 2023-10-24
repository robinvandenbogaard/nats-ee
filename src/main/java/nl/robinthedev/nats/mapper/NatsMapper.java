package nl.robinthedev.nats.mapper;

public interface NatsMapper {
  byte[] serialize(Object body);

  static NatsMapper createDefault() {
    return new DefaultJacksonNatsMapper();
  }
}
