package nl.robinthedev.nats.mapper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class DefaultJacksonNatsMapper implements NatsMapper {

  ObjectMapper mapper = new ObjectMapper().configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

  @Override
  public byte[] serialize(Object body) {
    try {
      return mapper.writeValueAsBytes(body);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
