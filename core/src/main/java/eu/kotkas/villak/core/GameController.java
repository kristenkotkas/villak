package eu.kotkas.villak.core;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * @author Kristen Kotkas
 */
@Controller
public class GameController {

  @MessageMapping("/request")
  @SendTo("/response")
  public Message message(Message message) {
    System.out.println(message);
    return message;
  }
}
