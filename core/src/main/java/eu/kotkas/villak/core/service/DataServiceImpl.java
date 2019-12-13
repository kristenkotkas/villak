package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.dao.GameDao;
import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;
import eu.kotkas.villak.core.model.enums.Action;
import eu.kotkas.villak.core.model.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kristen Kotkas
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class DataServiceImpl implements DataService {

  private final GameDao gameDao;
  private static Game currentState;
  private static final Map<Action, Reducer> REDUCERS = new HashMap<>();

  static {
    REDUCERS.put(Action.OPEN, Reducer.QUESTION_OPEN);
    REDUCERS.put(Action.CLOSE, Reducer.QUESTION_CLOSE);
    REDUCERS.put(Action.ANSWERED, Reducer.QUESTION_ANSWERED);
    REDUCERS.put(Action.SILVER, Reducer.QUESTION_SILVER);
    REDUCERS.put(Action.SCORE, Reducer.TEAM_SCORE);
    REDUCERS.put(Action.CATEGORY_SHOW, Reducer.CATEGORY_SHOW);
    REDUCERS.put(Action.CATEGORY_HIDE, Reducer.CATEGORY_HIDE);
    REDUCERS.put(Action.ACTIVE_ROUND, Reducer.ROUND_ACTIVE);
    REDUCERS.put(Action.PRESS_BUTTON, Reducer.PRESS_BUTTON);
    REDUCERS.put(Action.SHOW_QUICKEST, Reducer.SHOW_QUICKEST);
    REDUCERS.put(Action.RESET_BUTTON, Reducer.RESET_BUTTON);
    REDUCERS.put(Action.CLOSE_BUTTON, Reducer.CLOSE_BUTTON);
    REDUCERS.put(Action.TOGGLE_WINNER, Reducer.TOGGLE_WINNER);
    REDUCERS.put(Action.GET_CURRENT, Reducer.GET_CURRENT);
    REDUCERS.put(Action.SET_ADMIN_DEVICE_ID, Reducer.SET_ADMIN_DEVICE_ID);
    REDUCERS.put(Action.SET_CLIENT_DEVICE_ID, Reducer.SET_CLIENT_DEVICE_ID);
    REDUCERS.put(Action.SYNC_BUTTON, Reducer.SYNC_BUTTON);
    REDUCERS.put(Action.SET_TEAM_DEVICE_ID, Reducer.SET_TEAM_DEVICE_ID);
  }

  @Override
  public Game getNextState(List<Message> messages) {
    Game nextState;
    if (currentState == null) {
      nextState = GameMapper.MAPPER.invert(gameDao.getInitialGame());
      log.info(nextState);
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
      nextStage = REDUCERS.get(message.getAction()).reduce(nextStage, message);
    }
    return nextStage;
  }

}
