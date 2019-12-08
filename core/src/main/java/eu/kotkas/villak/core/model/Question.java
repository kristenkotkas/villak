package eu.kotkas.villak.core.model;

import eu.kotkas.villak.core.model.enums.QuestionState;
import eu.kotkas.villak.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Kristen Kotkas
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable {

  @Builder.Default
  private int id = IdUtil.getId();
  private int amount;
  private boolean silver;
  private String question;
  private String answer;
  @Builder.Default
  private QuestionState state = QuestionState.CLOSE;
  private String pictureUri;
  private String soundUri;

}
