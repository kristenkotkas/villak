package eu.kotkas.villak.core.villak.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "name"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDto implements Serializable {

  private final static long serialVersionUID = -4010167780327858052L;

  private String name;

}
