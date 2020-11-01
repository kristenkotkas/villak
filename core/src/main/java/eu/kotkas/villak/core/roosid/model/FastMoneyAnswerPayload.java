package eu.kotkas.villak.core.roosid.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FastMoneyAnswerPayload implements Serializable {
    private static final long serialVersionUID = -4069345223555042564L;
    private Long questionId;
    private String firstPlayerAnswer;
    private String secondPlayerAnswer;
    private Long firstPlayerScore;
    private Long secondPlayerScore;
}
