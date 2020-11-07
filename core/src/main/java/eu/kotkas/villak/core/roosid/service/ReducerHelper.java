package eu.kotkas.villak.core.roosid.service;

import eu.kotkas.villak.core.roosid.model.Action;
import eu.kotkas.villak.core.roosid.model.Answer;
import eu.kotkas.villak.core.roosid.model.AnswerState;
import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.Message;
import eu.kotkas.villak.core.roosid.model.Round;
import eu.kotkas.villak.core.roosid.model.Team;
import java.util.Optional;
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

    static Game detectWinner(Game clone, Message message) {
        clone.getRounds().stream()
            .filter(round -> round.isActive() && round.getAnsweringTeamId() != null && round.getWinnerTeamId() == null)
            .findFirst()
            .ifPresent(round -> clone.getTeams().stream()
                .filter(team -> team.getId() == round.getAnsweringTeamId())
                .findFirst()
                .ifPresent(answeringTeam -> {
                    if (message.getAction().equals(Action.TOGGLE_ANSWER)) {
                        if (answeringTeam.getCrossCount() < 3) {
                            boolean existsUnOpenedAnswers = round.getAnswers().stream()
                                .anyMatch(answer -> answer.getState().equals(AnswerState.CLOSED));
                            if (!existsUnOpenedAnswers) {
                                round.setWinnerTeamId(answeringTeam.getId());
                                round.setScoreToWin(round.getScore());
                                addWinnerScore(clone);
                            }
                        } else {
                            clone.getTeams().stream()
                                .filter(team -> team.getId() != round.getAnsweringTeamId())
                                .findFirst()
                                .ifPresent(nonAnsweringTeam -> {
                                    round.setWinnerTeamId(nonAnsweringTeam.getId());
                                    round.setScoreToWin(round.getScore());
                                    addWinnerScore(clone);
                                });
                        }
                    }
                    if (message.getAction().equals(Action.ADD_CROSS)) {
                        getOppositeTeam(clone, answeringTeam.getId())
                            .ifPresent(oppositeTeam -> {
                                if (oppositeTeam.getCrossCount() == 3) {
                                    round.setWinnerTeamId(answeringTeam.getId());
                                    round.setScoreToWin(round.getScore());
                                    addWinnerScore(clone);
                                }
                            });
                    }
                }));
        return clone;
    }

    private static void addWinnerScore(Game clone) {
        clone.getRounds().stream()
            .filter(round -> round.isActive() && round.getWinnerTeamId() != null)
            .findFirst()
            .ifPresent(round -> clone.getTeams().stream()
                .filter(team -> team.getId() == round.getWinnerTeamId())
                .findFirst()
                .ifPresent(winnerTeam -> {
                    winnerTeam.setScore(winnerTeam.getScore() + round.getScoreToWin());
                    clone.getLatestMessages().add(new Message(Action.PLAY_SHORT_THEME.name(), -1, null));
                })
            );
    }

    static Optional<Round> getActiveRound(Game clone) {
        return clone.getRounds().stream()
            .filter(Round::isActive)
            .findFirst();
    }

    static Optional<Team> getCurrentTeam(Game clone, Long teamId) {
        return clone.getTeams().stream()
            .filter(t -> teamId != null && t.getId() == teamId)
            .findFirst();
    }

    static Optional<Team> getOppositeTeam(Game clone, Long teamId) {
        return clone.getTeams().stream()
            .filter(t -> teamId != null && t.getId() != teamId)
            .findFirst();
    }

}
