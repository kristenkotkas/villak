package eu.kotkas.villak.core.villak.service;

import eu.kotkas.villak.core.villak.model.Category;
import eu.kotkas.villak.core.villak.model.Game;
import eu.kotkas.villak.core.villak.model.Message;
import eu.kotkas.villak.core.villak.model.Question;
import eu.kotkas.villak.core.villak.model.Round;
import eu.kotkas.villak.core.villak.model.Team;
import org.apache.commons.lang3.SerializationUtils;

import java.util.function.Consumer;

/**
 * @author Kristen Kotkas
 */
class ReducerHelper {

  static Game getQuestionNextStage(Game game, Message message, Consumer<Question> mutator) {
    Game clone = SerializationUtils.clone(game);
    clone.getRounds()
      .forEach(round -> round.getCategories()
        .forEach(category -> category.getQuestions().stream()
          .filter(question -> question.getId() == message.getId())
          .forEach(mutator)
        ));
    return clone;
  }

  static Game getTeamNextStage(Game game, Message message, Consumer<Team> mutator) {
    Game clone = SerializationUtils.clone(game);
    clone.getTeams().stream()
      .filter(team -> team.getId() == message.getId())
      .forEach(mutator);
    return clone;
  }

  static Game getCategoryNextStage(Game game, Message message, Consumer<Category> mutator) {
    Game clone = SerializationUtils.clone(game);
    clone.getRounds()
      .forEach(round -> round.getCategories().stream()
        .filter(category -> category.getId() == message.getId())
        .forEach(mutator));
    return clone;
  }

  static Game getRoundNextStage(Game game, Message message, Consumer<Round> roundConsumer) {
    Game clone = SerializationUtils.clone(game);
    clone.getRounds()
      .forEach(roundConsumer);
    return clone;
  }

}
