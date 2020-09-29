package eu.kotkas.villak.core.villak.service;

import eu.kotkas.villak.core.villak.dao.VillakDao;
import eu.kotkas.villak.core.villak.model.Game;
import eu.kotkas.villak.core.villak.model.Message;
import eu.kotkas.villak.core.villak.model.enums.Action;
import eu.kotkas.villak.core.villak.model.mapper.GameMapper;
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
public class VillakServiceImpl implements VillakService {

  private final VillakDao villakDao;
  private static Game currentState;

  @Override
  public Game getNextState(List<Message> messages) {
    Game nextState;
    if (currentState == null ) {
      nextState = GameMapper.MAPPER.invert(villakDao.getInitialGame());
    } else if (messages.size() == 1 && messages.get(0).getAction().equals(Action.RESTART_GAME)) {
      nextState = reduce(GameMapper.MAPPER.invert(villakDao.getInitialGame()), messages);
    } else {
      messages.add(new Message(Action.RESET_SHOULD_REFRESH.name(), -1, -1L));
      nextState = reduce(currentState, messages);
    }
    log.info(nextState);
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
