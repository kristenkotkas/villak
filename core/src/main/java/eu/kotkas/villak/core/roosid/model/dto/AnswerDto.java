package eu.kotkas.villak.core.roosid.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "displayNumber",
    "answer",
    "amount"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto implements Serializable {
    private static final long serialVersionUID = 6657470876758833253L;

    private long displayNumber;
    private String answer;
    private long amount;
}
