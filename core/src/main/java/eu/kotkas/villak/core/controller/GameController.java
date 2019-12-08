package eu.kotkas.villak.core.controller;

import eu.kotkas.villak.core.model.Game;
import eu.kotkas.villak.core.model.Message;
import eu.kotkas.villak.core.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author Kristen Kotkas
 */
@Controller
@RequiredArgsConstructor
@Log4j2
public class GameController {

  private final DataService dataService;

  @MessageMapping("/request")
  @SendTo("/response")
  public Game message(List<Message> messages) {
    log.info(messages);
    return dataService.getNextState(messages);
  }
}
