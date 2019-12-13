package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;
import eu.kotkas.villak.core.model.Team;
import eu.kotkas.villak.core.model.enums.NameState;
import eu.kotkas.villak.core.model.enums.QuestionState;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

import java.util.Comparator;
import java.util.function.BiFunction;

import static eu.kotkas.villak.core.service.ReducerHelper.*;

/**
 * @author Kristen Kotkas
 */
@RequiredArgsConstructor
public enum Reducer {

  QUESTION_OPEN((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.OPEN))),
  QUESTION_CLOSE((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.CLOSE))),
  QUESTION_ANSWERED((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.ANSWERED))),
  QUESTION_SILVER((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.SILVER))),
  TEAM_SCORE((game, message) -> getTeamNextStage(game, message, t -> t.setScore(t.getScore() + message.getPayload().intValue()))),
  CATEGORY_SHOW((game, message) -> getCategoryNextStage(game, message, c -> c.setNameState(NameState.CATEGORY_SHOW))),
  CATEGORY_HIDE((game, message) -> getCategoryNextStage(game, message, c -> c.setNameState(NameState.CATEGORY_HIDE))),
  ROUND_ACTIVE((game, message) -> getRoundNextStage(game, message, r -> r.setActive(r.getId() == message.getId()))),
  PRESS_BUTTON((game, message) -> getTeamNextStage(game, message, t -> {
    t.setHavePressed(true);
    game.getTeams().stream()
      .filter(team -> team.getId() == message.getId())
      .findFirst()
      .ifPresent(team -> t.setTimePressed(message.getPayload() - team.getTimeSynced()));
  })),
  SHOW_QUICKEST((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getTeams().stream()
      .filter(team -> team.getTimePressed() > 0)
      .min(Comparator.comparingLong(Team::getTimePressed))
      .ifPresent(team -> team.setQuickest(true));
    return clone;
  }),
  RESET_BUTTON((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getTeams().forEach(team -> {
      team.setHavePressed(false);
      team.setTimePressed(0L);
      team.setQuickest(false);
    });
    return clone;
  }),
  CLOSE_BUTTON((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getTeams().forEach(team -> {
      team.setHavePressed(true);
      team.setTimePressed(0L);
      team.setQuickest(false);
    });
    return clone;
  }),
  TOGGLE_WINNER((game, message) -> getTeamNextStage(game, message, t -> t.setWinner(!t.isWinner()))),
  GET_CURRENT((game, message) -> game),
  SET_ADMIN_DEVICE_ID((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getSettings().setAdminDeviceId(message.getPayload());
    return clone;
  }),
  SET_CLIENT_DEVICE_ID((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getSettings().setGameDeviceId(message.getPayload());
    return clone;
  }),
  SYNC_BUTTON((game, message) -> getTeamNextStage(game, message, team -> team.setTimeSynced(message.getPayload()))),
  SET_TEAM_DEVICE_ID((game, message) -> getTeamNextStage(game, message, team -> team.setDeviceId(message.getPayload()))),
  ;

  private final BiFunction<Game, Message, Game> reducer;

  Game reduce(Game game, Message message) {
    return this.reducer.apply(game, message);
  }
}
