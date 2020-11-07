package eu.kotkas.villak.core.roosid.controller;

import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.model.Message;
import eu.kotkas.villak.core.roosid.service.RoosidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.SerializationUtils;
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

        Game clone = SerializationUtils.clone(nextState);
        messages.forEach(message -> clone.getLatestMessages().add(message));

        return clone;
    }

}
