package eu.kotkas.villak.core.villak.controller;

import eu.kotkas.villak.core.villak.model.Game;
import eu.kotkas.villak.core.villak.model.Message;
import eu.kotkas.villak.core.villak.service.VillakService;
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
public class VillakController {

  private final VillakService villakService;

  @MessageMapping("/request")
  @SendTo("/response")
  public Game message(List<Message> messages) {
    log.info(messages);
    return villakService.getNextState(messages);
  }
}