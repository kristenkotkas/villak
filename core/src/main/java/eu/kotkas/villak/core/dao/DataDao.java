package eu.kotkas.villak.core.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.kotkas.villak.core.Main;
import eu.kotkas.villak.core.model.dto.GameDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;

/**
 * @author Kristen Kotkas
 */
@Repository
@Getter
@Setter
@Log4j2
public class DataDao {

  public GameDto getInitialGame() {
    ObjectMapper mapper = new ObjectMapper();
    try {
      GameDto game = mapper.readValue(new File(Main.getDataFileLocation()), GameDto.class);
      log.info(game);
      return game;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
