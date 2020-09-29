package eu.kotkas.villak.core.roosid.service;

import eu.kotkas.villak.core.roosid.model.Answer;
import eu.kotkas.villak.core.roosid.model.AnswerState;
import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.Message;
import eu.kotkas.villak.core.roosid.model.Round;
import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

@RequiredArgsConstructor
public enum Reducer {

    GET_CURRENT((game, message) -> game),
    SET_ACTIVE_ROUND((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getRounds().forEach(round -> {
            round.setActive(round.getId() == message.getId());
        });
        return clone;
    }),
    SET_INACTIVE_ROUND((game, message) -> {
        return ReducerHelper.getRoundNextStage(game, message, r -> r.setActive(false));
    }),
    TOGGLE_ANSWER((game, message) -> {
        Game clone = ReducerHelper.getAnswerNextStage(game, message, a -> {
            if (a.getState().equals(AnswerState.OPENED)) {
                a.setState(AnswerState.CLOSED);
            } else if (a.getState().equals(AnswerState.CLOSED)) {
                a.setState(AnswerState.OPENED);
            }
        });
        clone.getRounds()
            .forEach(round -> {
                long roundScore = round.getAnswers().stream()
                    .filter(a -> a.getState().equals(AnswerState.OPENED))
                    .mapToLong(Answer::getAmount).sum() * round.getMultiplayer();
                round.setScore(roundScore);
            });
        return clone;
    }),
    ADD_CURRENT_SCORE_TO_TEAM((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getRounds().stream().filter(Round::isActive).findFirst().ifPresent(
            round -> clone.getTeams().stream()
                .filter(team -> team.getId() == message.getId())
                .findFirst()
                .ifPresent(team -> team.setScore(team.getScore() + round.getScore()))
        );
        return clone;
    }),
    RESET_SCORE((game, message) -> {
        return ReducerHelper.getTeamNextStage(game, message, t -> t.setScore(0));
    }),
    ADD_CROSS((game, message) -> {
        return ReducerHelper.getTeamNextStage(game, message, t -> t.setCrossCount(t.getCrossCount() + 1));
    }),
    RESET_CROSS((game, message) -> {
        return ReducerHelper.getTeamNextStage(game, message, t -> t.setCrossCount(0));
    })
    ;


    private final BiFunction<Game, Message, Game> reducer;

    Game reduce(Game game, Message message) {
        return this.reducer.apply(game, message);
    }

}
