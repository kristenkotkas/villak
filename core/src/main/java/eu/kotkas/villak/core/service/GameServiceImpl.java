package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.dao.GameDao;
import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;
import eu.kotkas.villak.core.model.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kristen Kotkas
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class GameServiceImpl implements GameService {

  private final GameDao gameDao;
  private static Game currentState;

  @Override
  public Game getNextState(List<Message> messages) {
    Game nextState;
    if (currentState == null) {
      nextState = GameMapper.MAPPER.invert(gameDao.getInitialGame());
      //log.info(nextState);
    } else {
      nextState = reduce(currentState, messages);
    }
    currentState = nextState;
    return nextState;
  }

  @Override
  public Game reduce(Game game, List<Message> messages) {
    Game nextStage = game;
    for (Message message : messages) {
      nextStage = Reducer.valueOf(message.getAction().name()).reduce(nextStage, message);
    }
    return nextStage;
  }

}
