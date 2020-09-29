package eu.kotkas.villak.core.roosid.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer implements Serializable {
    private long id;
    private long amount;
    private String answer;
    private AnswerState state;
    private long displayNumber;
}