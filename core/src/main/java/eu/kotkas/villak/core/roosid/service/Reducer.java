package eu.kotkas.villak.core.roosid.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.kotkas.villak.core.roosid.model.Action;
import eu.kotkas.villak.core.roosid.model.Answer;
import eu.kotkas.villak.core.roosid.model.AnswerState;
import eu.kotkas.villak.core.roosid.model.FastMoneyAnswerPayload;
import eu.kotkas.villak.core.roosid.model.FastMoneyPlayerResponse;
import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.Message;
import eu.kotkas.villak.core.roosid.model.Round;
import eu.kotkas.villak.core.util.IdUtil;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;

@RequiredArgsConstructor
public enum Reducer {

    GET_CURRENT((game, message) -> game),
    SET_ACTIVE_ROUND((game, message) -> ReducerHelper.getRoundNextStage(game, message, r -> r.setActive(true))),
    SET_INACTIVE_ROUND((game, message) -> ReducerHelper.getRoundNextStage(game, message, r -> r.setActive(false))),
    SET_FAST_MONEY_ACTIVE((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getFastMoney().setActive(true);
        return clone;
    }),
    SET_FAST_MONEY_INACTIVE((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getFastMoney().setActive(false);
        return clone;
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
        return ReducerHelper.detectWinner(clone, message);
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
        Game clone = SerializationUtils.clone(game);

        Optional<Round> activeRoundOptional = ReducerHelper.getActiveRound(clone);

        if (activeRoundOptional.isPresent()) {
            Round activeRound = activeRoundOptional.get();
            ReducerHelper.getCurrentTeam(clone, activeRound.getAnsweringTeamId())
                .ifPresent(answeringTeam -> {
                    if (answeringTeam.getCrossCount() < 3) {
                        answeringTeam.setCrossCount(answeringTeam.getCrossCount() + 1);
                    } else {
                        ReducerHelper.getOppositeTeam(clone, answeringTeam.getId())
                            .ifPresent(oppositeTeam -> oppositeTeam.setCrossCount(3));
                    }
                });
            if (activeRound.getWinnerTeamId() == null) {
                clone.getLatestMessages().add(new Message(Action.SHOW_GLOBAL_CROSS.name(), -1, null));
            }
        } else {
            clone.getLatestMessages().add(new Message(Action.SHOW_GLOBAL_CROSS.name(), -1, null));
        }
        return ReducerHelper.detectWinner(clone, message);
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
    CHANGE_BUFFER((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        if (clone.getSettings() != null) {
            clone.getSettings().setBufferSize(clone.getSettings().getBufferSize() + ((Integer) message.getPayload()));
        }
        return clone;
    }),
    CHANGE_ZOOM((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        if (clone.getSettings() != null) {
            clone.getSettings().setBoardZoom(clone.getSettings().getBoardZoom() + ((Integer) message.getPayload()));
        }
        return clone;
    }),
    SET_ANSWERING_FOR_ROUND((game, message) -> ReducerHelper.getActiveRoundNextStage(game, message, r -> {
        r.setAnsweringTeamId(message.getId() == -1 ? null : message.getId());
    })),
    SAVE_PLAYER_ANSWERS((game, message) -> {
        Game clone = SerializationUtils.clone(game);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<FastMoneyAnswerPayload> payload = objectMapper
                .readValue(objectMapper.writeValueAsString(message.getPayload()), new TypeReference<List<FastMoneyAnswerPayload>>() {
                });

            clone.getFastMoney().getQuestions().forEach(q -> {
                Optional<FastMoneyAnswerPayload> data = payload.stream()
                    .filter(fastMoneyAnswerPayload -> fastMoneyAnswerPayload.getQuestionId().equals(q.getId()))
                    .findFirst();
                if (data.isPresent()) {
                    q.getFirstPlayer().setId(IdUtil.getId());
                    q.getFirstPlayer().setAnswer(data.get().getFirstPlayerAnswer());
                    q.getFirstPlayer().setScore(data.get().getFirstPlayerScore());
                    q.getSecondPlayer().setId(IdUtil.getId());
                    q.getSecondPlayer().setAnswer(data.get().getSecondPlayerAnswer());
                    q.getSecondPlayer().setScore(data.get().getSecondPlayerScore());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        long currentScore = clone.getFastMoney().getQuestions().stream()
            .flatMap(q -> Stream.of(q.getFirstPlayer(), q.getSecondPlayer()))
            .map(FastMoneyPlayerResponse::getScore)
            .filter(Objects::nonNull)
            .mapToLong(Long::longValue)
            .sum();

        clone.getFastMoney().setCurrentScore(currentScore);
        if (game.getFastMoney() != null && game.getFastMoney().getCurrentScore() < 200
            && clone.getFastMoney() != null && clone.getFastMoney().getCurrentScore() >= 200) {
            clone.getLatestMessages().add(new Message(Action.PLAY_SHORT_THEME.name(), -1, null));
        }

        return clone;
    }),
    TOGGLE_FAST_MONEY_ANSWER((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getFastMoney().getQuestions().forEach(q -> {
            if (q.getFirstPlayer().getId() == message.getId()) {
                q.getFirstPlayer().setQuestionVisible(!q.getFirstPlayer().isQuestionVisible());
            }
            if (q.getSecondPlayer().getId() == message.getId()) {
                q.getSecondPlayer().setQuestionVisible(!q.getSecondPlayer().isQuestionVisible());
            }
        });
        return clone;
    }),
    TOGGLE_FIRST_PLAYER_VISIBLE((game, message) -> {
        Game clone = SerializationUtils.clone(game);
        clone.getFastMoney().setHideFirstPlayerScore(!clone.getFastMoney().isHideFirstPlayerScore());
        return clone;
    });

    private final BiFunction<Game, Message, Game> reducer;

    Game reduce(Game game, Message message) {
        return this.reducer.apply(game, message);
    }

}
