package eu.kotkas.villak.core.model;

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
public class Game implements Serializable {

  private List<Team> teams;
  private List<Round> rounds;

}
