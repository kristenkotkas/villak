package eu.kotkas.villak.core.villak.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

  private final static long serialVersionUID = 7622303857553870526L;

  @Builder.Default
  private List<TeamDto> teams = new ArrayList<>();

  @Builder.Default
  private List<RoundDto> rounds = new ArrayList<>();

}
