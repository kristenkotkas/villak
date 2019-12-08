package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.dao.DataDao;
import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;
import eu.kotkas.villak.core.model.enums.Action;
import eu.kotkas.villak.core.model.mapper.GameMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kristen Kotkas
 */
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {

  private final DataDao dataDao;
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
  }

  @Override
  public Game getNextState(Message message) {
    Game nextState;
    if (currentState == null) {
      nextState = GameMapper.MAPPER.invert(dataDao.getInitialGame());
    } else {
      nextState = reduce(currentState, message);
    }
    currentState = nextState;
    return nextState;
  }

  @Override
  public Game reduce(Game game, Message message) {
    return REDUCERS.get(message.getAction()).reduce(game, message);
  }

}
