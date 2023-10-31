package nl.robinthedev.nats.mapper;

public interface NatsMapper {

  static NatsMapper createDefault() {
    return new DefaultJacksonNatsMapper();
  }

  byte[] serialize(Object body);

  <T> T deserialize(byte[] data, Class<T> type);
}
