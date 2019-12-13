package eu.kotkas.villak.core.model;

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
public class Team implements Serializable {

  @Builder.Default
  private int id = IdUtil.getId();
  private String name;
  @Builder.Default
  private int score = 0;
  private boolean havePressed;
  @Builder.Default
  private Long timePressed = 0L;
  @Builder.Default
  private Long timeSynced = 0L;
  private Long deviceId;
  private boolean quickest;
  private boolean winner;

}
