package eu.kotkas.villak.core.roosid.service;

import eu.kotkas.villak.core.roosid.model.Answer;
import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.Message;
import eu.kotkas.villak.core.roosid.model.Round;
import eu.kotkas.villak.core.roosid.model.Team;
import java.util.function.Consumer;
import org.apache.commons.lang3.SerializationUtils;

public class ReducerHelper {

    static Game getRoundNextStage(Game game, Message message, Consumer<Round> roundMutator) {
        Game clone = SerializationUtils.clone(game);
        clone.getRounds().stream()
            .filter(round -> round.getId() == message.getId())
            .forEach(roundMutator);
        return clone;
    }

    static Game getActiveRoundNextStage(Game game, Message message, Consumer<Round> activeRoundMutator) {
        Game clone = SerializationUtils.clone(game);
        clone.getRounds().stream()
            .filter(Round::isActive)
            .forEach(activeRoundMutator);
        return clone;
    }

    static Game getAnswerNextStage(Game game, Message message, Consumer<Answer> activeRoundMutator) {
        Game clone = SerializationUtils.clone(game);
        clone.getRounds()
            .forEach(round -> round.getAnswers()
                .stream()
                .filter(answer -> answer.getId() == message.getId())
                .forEach(activeRoundMutator));
        return clone;
    }

    static Game getTeamNextStage(Game game, Message message, Consumer<Team> teamMutator) {
        Game clone = SerializationUtils.clone(game);
        clone.getTeams().stream().filter(t -> t.getId() == message.getId()).forEach(teamMutator);
        return clone;
    }

}
