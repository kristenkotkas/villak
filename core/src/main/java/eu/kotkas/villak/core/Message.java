package eu.kotkas.villak.core;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author Kristen Kotkas
 */
@lombok.Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Message {
  private Long event;
  private int id;
  private Long payload;
}
