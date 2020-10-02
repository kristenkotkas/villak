package eu.kotkas.villak.core.roosid.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "question",
    "multiplayer",
    "answers"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoundDto implements Serializable {
    private static final long serialVersionUID = -8344465886809305588L;

    private String question;
    private long multiplayer;
    private List<AnswerDto> answers;
}
