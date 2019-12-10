package eu.kotkas.villak.core.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.kotkas.villak.core.Main;
import eu.kotkas.villak.core.model.dto.GameDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

/**
 * @author Kristen Kotkas
 */
@Repository
@Getter
@Setter
public class GameDao {

  public GameDto getInitialGame() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      return mapper.readValue(new File(Main.getDataFileLocation()), GameDto.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
