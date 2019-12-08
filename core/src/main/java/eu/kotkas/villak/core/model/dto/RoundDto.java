package eu.kotkas.villak.core.model.dto;

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
  "categories"
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoundDto implements Serializable {

  private final static long serialVersionUID = -2533849945083025989L;

  @Builder.Default
  private List<CategoryDto> categories = new ArrayList<>();

}
