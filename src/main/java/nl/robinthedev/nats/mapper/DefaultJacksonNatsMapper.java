package nl.robinthedev.nats.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

class DefaultJacksonNatsMapper implements NatsMapper {

  final ObjectMapper mapper =
      new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

  @Override
  public byte[] serialize(Object body) {
    try {
      return mapper.writeValueAsBytes(body);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T> T deserialize(byte[] body, Class<T> type) {
    try {
      return mapper.readValue(body, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
