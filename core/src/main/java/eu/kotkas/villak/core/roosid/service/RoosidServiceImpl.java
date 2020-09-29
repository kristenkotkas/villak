package eu.kotkas.villak.core.roosid.service;

import eu.kotkas.villak.core.roosid.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class RoosidServiceImpl implements RoosidService {

    private static Game currentState;

    @Override
    public Game getNextState(List<Message> messages) {
        Game nextState;
        if (currentState == null) {
            nextState = getInitialGame();
        } else {
            nextState = reduce(currentState, messages);
        }
        currentState = nextState;
        return nextState;
    }

    @Override
    public Game reduce(Game game, List<Message> messages) {
        Game nextState = game;
        for (Message message : messages) {
            nextState = Reducer.valueOf(message.getAction().name()).reduce(nextState, message);
        }
        return nextState;
    }

    private Game getInitialGame() {
        return Game.builder()
            .teams(List.of(
                Team.builder().id(0).name("Arendajad").score(0).crossCount(0).build(),
                Team.builder().id(1).name("Testijad").score(0).crossCount(0).build()))
            .rounds(List.of(
                Round.builder().id(0).question("Nimeta midagi hääd").multiplayer(1).slots(6).score(0).active(false)
                    .answers(List.of(
                        Answer.builder().id(0).displayNumber(1).state(AnswerState.CLOSED).answer("Pizza").amount(25).build(),
                        Answer.builder().id(1).displayNumber(2).state(AnswerState.CLOSED).answer("Komm").amount(13).build(),
                        Answer.builder().id(2).displayNumber(3).state(AnswerState.CLOSED).answer("Sushi").amount(8).build(),
                        Answer.builder().id(3).displayNumber(4).state(AnswerState.CLOSED).answer("Bugi").amount(7).build(),
                        Answer.builder().id(4).displayNumber(5).state(AnswerState.CLOSED).answer("Vaba päev").amount(3).build(),
                        Answer.builder().id(5).displayNumber(6).state(AnswerState.CLOSED).answer("Töö").amount(2).build())).build(),
                Round.builder().id(1).question("Nimeta lemmik pähkel").multiplayer(2).slots(6).score(0).active(false)
                    .answers(List.of(
                        Answer.builder().id(6).displayNumber(1).state(AnswerState.CLOSED).answer("Pistaatsia").amount(35).build(),
                        Answer.builder().id(7).displayNumber(2).state(AnswerState.CLOSED).answer("Kreeka").amount(20).build(),
                        Answer.builder().id(8).displayNumber(3).state(AnswerState.CLOSED).answer("Ümar").amount(4).build(),
                        Answer.builder().id(9).displayNumber(4).state(AnswerState.CLOSED).answer("Sarapuu").amount(2).build(),
                        Answer.builder().id(10).displayNumber(5).state(AnswerState.CLOSED).answer("Maitsev").amount(1).build())).build(),
                Round.builder().id(2).question("Mida kontorist koju vead").multiplayer(3).slots(6).score(0).active(false)
                    .answers(List.of(
                        Answer.builder().id(11).displayNumber(1).state(AnswerState.CLOSED).answer("Komm").amount(35).build(),
                        Answer.builder().id(12).displayNumber(2).state(AnswerState.CLOSED).answer("Banaan").amount(20).build(),
                        Answer.builder().id(13).displayNumber(3).state(AnswerState.CLOSED).answer("Post-It").amount(4).build(),
                        Answer.builder().id(14).displayNumber(4).state(AnswerState.CLOSED).answer("Sarapuu").amount(2).build())).build()
            ))
            .build();
    }

}
