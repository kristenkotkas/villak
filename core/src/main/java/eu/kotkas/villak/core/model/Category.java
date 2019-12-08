package eu.kotkas.villak.core.model;

import eu.kotkas.villak.core.model.enums.NameState;
import eu.kotkas.villak.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kristen Kotkas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

  @Builder.Default
  private int id = IdUtil.getId();
  private String name;
  private List<Question> questions;
  @Builder.Default
  private NameState nameState = NameState.CATEGORY_HIDE;

}
