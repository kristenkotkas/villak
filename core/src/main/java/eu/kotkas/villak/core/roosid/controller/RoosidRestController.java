package eu.kotkas.villak.core.roosid.controller;

import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.service.RoosidService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/roosid")
@RequiredArgsConstructor
public class RoosidRestController {

    private final RoosidService roosidService;

    @GetMapping
    public Game getGame() {
        return roosidService.getInitialGame();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createGame(@RequestBody Game game) {
        System.out.println(game);
        roosidService.createGame(game);
    }
}
