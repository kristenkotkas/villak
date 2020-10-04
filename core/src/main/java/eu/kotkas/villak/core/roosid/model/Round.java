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
    private static final long serialVersionUID = 2320362160409094472L;
    private long id;
    private String question;
    private List<Answer> answers;
    private long multiplayer;
    private long score;
    private long scoreToWin;
    private Long winnerTeamId;
    private Long answeringTeamId;
    private boolean active;
}
