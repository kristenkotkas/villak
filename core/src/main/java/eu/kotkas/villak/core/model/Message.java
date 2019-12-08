package eu.kotkas.villak.core.model;

import lombok.Data;

/**
 * @author Kristen Kotkas
 */
@Getter
@Setter
@ToString
public class Message implements Serializable{
  private Action action;
  private long id;
  private Integer payload;

  public Message(String action, long id, Integer payload) {
    this.action = Action.valueOf(action);
    this.id = id;
    this.payload = payload;
  }

}
