package eu.kotkas.villak.core.roosid.service;

import eu.kotkas.villak.core.roosid.dao.RoosidDao;
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

    private final RoosidDao roosidDao;

    @Override
    public Game getInitialGame() {
        return roosidDao.getInitialGame();
    }

    @Override
    public void createGame(Game game) {
        roosidDao.saveGame(game);
        currentState = game;
    }

    @Override
    public Game getNextState(List<Message> messages) {
        Game nextState;
        if (currentState == null) {
            nextState = getInitialGame();
        } else {
            nextState = reduce(currentState, messages);
        }
        currentState = nextState;
        if (nextState == null) {
            nextState = new Game();
        }
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

}
