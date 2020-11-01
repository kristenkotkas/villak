package eu.kotkas.villak.core.roosid.model;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message implements Serializable {
    private static final long serialVersionUID = 4379535559179277332L;
    private Action action;
    private long id;
    private Object payload;

    public Message(String action, long id, Object payload) {
        this.action = Action.valueOf(action);
        this.id = id;
        this.payload = payload;
    }
}
