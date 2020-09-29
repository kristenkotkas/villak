package eu.kotkas.villak.core.villak.service;

import eu.kotkas.villak.core.villak.model.Game;
import eu.kotkas.villak.core.villak.model.Message;

import java.util.List;

/**
 * @author Kristen Kotkas
 */
public interface VillakService {

  Game getNextState(List<Message> messages);

  Game reduce(Game game, List<Message> messages);

}
