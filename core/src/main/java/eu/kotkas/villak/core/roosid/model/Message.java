package eu.kotkas.villak.core.roosid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {
    private Action action;
    private long id;
    private Long payload;

    public Message(String action, long id, Long payload) {
        this.action = Action.valueOf(action);
        this.id = id;
        this.payload = payload;
    }
}
