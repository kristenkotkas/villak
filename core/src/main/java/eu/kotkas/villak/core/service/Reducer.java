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

import static eu.kotkas.villak.core.service.ReducerHelper.getCategoryNextStage;
import static eu.kotkas.villak.core.service.ReducerHelper.getQuestionNextStage;
import static eu.kotkas.villak.core.service.ReducerHelper.getRoundNextStage;
import static eu.kotkas.villak.core.service.ReducerHelper.getTeamNextStage;

/**
 * @author Kristen Kotkas
 */
@RequiredArgsConstructor
public enum Reducer {

  QUESTION_OPEN((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.OPEN))),
  QUESTION_CLOSE((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.CLOSE))),
  QUESTION_ANSWERED((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.ANSWERED))),
  QUESTION_SILVER((game, message) -> getQuestionNextStage(game, message, q -> q.setState(QuestionState.SILVER))),
  TEAM_SCORE((game, message) -> getTeamNextStage(game, message, t -> t.setScore(t.getScore() + message.getPayload()))),
  CATEGORY_SHOW((game, message) -> getCategoryNextStage(game, message, c -> c.setNameState(NameState.CATEGORY_SHOW))),
  CATEGORY_HIDE((game, message) -> getCategoryNextStage(game, message, c -> c.setNameState(NameState.CATEGORY_HIDE))),
  ROUND_ACTIVE((game, message) -> getRoundNextStage(game, message, r -> r.setActive(r.getId() == message.getId()))),
  PRESS_BUTTON((game, message) -> getTeamNextStage(game, message, t -> {
    t.setHavePressed(true);
    t.setTimePressed(message.getPayload());
  })),
  SHOW_QUICKEST((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getTeams().stream()
      .filter(team -> team.getTimePressed() > 0)
      .min(Comparator.comparingInt(Team::getTimePressed))
      .ifPresent(team -> team.setQuickest(true));
    return clone;
  }),
  RESET_BUTTON((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getTeams().forEach(team -> {
      team.setHavePressed(false);
      team.setTimePressed(0);
      team.setQuickest(false);
    });
    return clone;
  }),
  CLOSE_BUTTON((game, message) -> {
    Game clone = SerializationUtils.clone(game);
    clone.getTeams().forEach(team -> {
      team.setHavePressed(true);
      team.setTimePressed(0);
      team.setQuickest(false);
    });
    return clone;
  }),
  TOGGLE_WINNER((game, message) -> getTeamNextStage(game, message, t -> t.setWinner(!t.isWinner()))),
  GET_CURRENT((game, message) -> game),
  ;

  private final BiFunction<Game, Message, Game> reducer;

  Game reduce(Game game, Message message) {
    return this.reducer.apply(game, message);
  }
}
