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
                if (round.getScoreToWin() == 0) {
                    round.setScore(roundScore);
                }
            });
        return clone;
    }),
    ADD_CURRENT_SCORE_TO_TEAM((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getRounds().stream().filter(Round::isActive).findFirst().ifPresent(
            round -> clone.getTeams().stream()
                .filter(team -> team.getId() == message.getId())
                .findFirst()
                .ifPresent(team -> team.setScore(team.getScore() + round.getScoreToWin()))
        );
        return clone;
    }),
    RESET_SCORE((game, message) -> {
        return ReducerHelper.getTeamNextStage(game, message, t -> t.setScore(0));
    }),
    ADD_CROSS((game, message) -> {
        return ReducerHelper.getTeamNextStage(game, message, t -> t.setCrossCount(t.getCrossCount() + message.getPayload()));
    }),
    RESET_CROSS((game, message) -> {
        return ReducerHelper.getTeamNextStage(game, message, t -> t.setCrossCount(0));
    }),
    RESET_CROSS_ALL((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getTeams().forEach(team -> team.setCrossCount(0));
        return clone;
    }),
    PLAY_SHORT_THEME((game, message) -> game),
    STOP_SHORT_THEME((game, message) -> game),
    PLAY_INTRO((game, message) -> game),
    STOP_INTRO((game, message) -> game),
    RESTART_GAME((game, message) -> game),
    SET_SCORE_TO_WIN((game, message) -> {
        return ReducerHelper.getActiveRoundNextStage(game, message, r -> r.setScoreToWin(r.getScore()));
    }),
    SET_ROUND_WINNER((game, message) -> ReducerHelper.getActiveRoundNextStage(game, message, r -> r.setWinnerTeamId(message.getId()))),
    CHANGE_BUFFER((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        if (clone.getSettings() != null) {
            clone.getSettings().setBufferSize(clone.getSettings().getBufferSize() + message.getPayload());
        }
        return clone;
    }),
    CHANGE_ZOOM((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        if (clone.getSettings() != null) {
            clone.getSettings().setBoardZoom(clone.getSettings().getBoardZoom() + message.getPayload());
        }
        return clone;
    })
    ;

    private final BiFunction<Game, Message, Game> reducer;

    Game reduce(Game game, Message message) {
        return this.reducer.apply(game, message);
    }

}
