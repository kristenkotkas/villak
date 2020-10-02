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
    "teams",
    "rounds"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDto implements Serializable {
    private static final long serialVersionUID = -1576553268456891195L;
    private List<TeamDto> teams;
    private List<RoundDto> rounds;
}
