package eu.kotkas.villak.core.roosid.service;

import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.Message;

import java.util.List;

public interface RoosidService {

    Game getNextState(List<Message> messages);

    Game reduce(Game game, List<Message> messages);

}
