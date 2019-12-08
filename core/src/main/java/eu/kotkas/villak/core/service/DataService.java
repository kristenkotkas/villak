package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;

/**
 * @author Kristen Kotkas
 */
public interface DataService {

  Game getNextState(Message message);

  Game reduce(Game game, Message message);

}
