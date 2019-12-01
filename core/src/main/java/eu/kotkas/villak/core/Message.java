package eu.kotkas.villak.core;

import lombok.Data;

/**
 * @author Kristen Kotkas
 */
@Data
public class Message {
  private Long event;
  private Long id;
  private Long payload;

  @Override
  public String toString() {
    return String.format("event=%d, id=%d, payload=%d", event, id, payload);
  }
}
