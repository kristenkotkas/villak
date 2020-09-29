package eu.kotkas.villak.core.roosid.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Round implements Serializable {
    private long id;
    private String question;
    private List<Answer> answers;
    private long multiplayer;
    private long score;
    private long slots;
    private boolean active;
}