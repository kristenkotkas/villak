package eu.kotkas.villak.core.roosid.controller;

import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.Message;
import eu.kotkas.villak.core.roosid.service.RoosidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class RoosidController {

    private final RoosidService roosidService;

    @MessageMapping("/roosid_request")
    @SendTo("/roosid_response")
    public Game message(List<Message> messages) {
        log.info(messages);
        Game nextState = roosidService.getNextState(messages);
        nextState.setLatestMessages(messages);
        return nextState;
    }

}
