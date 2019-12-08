package eu.kotkas.villak.core.service;

import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;

import java.util.List;

/**
 * @author Kristen Kotkas
 */
public interface DataService {

  Game getNextState(List<Message> messages);

  Game reduce(Game game, List<Message> messages);

}
