package eu.kotkas.villak.core.roosid.controller;

import eu.kotkas.villak.core.roosid.model.Game;
import eu.kotkas.villak.core.roosid.service.RoosidService;
import eu.kotkas.villak.core.roosid.useCase.GetGamePdfUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/roosid")
@RequiredArgsConstructor
public class RoosidRestController {

    private final RoosidService roosidService;
    private final GetGamePdfUseCase getGamePdfUseCase;

    @GetMapping
    public Game getGame() {
        return roosidService.getInitialGame();
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createGame(@RequestBody Game game) {
        System.out.println(game);
        roosidService.createGame(game);
    }

    @GetMapping(path = "download", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadPdf() {
        byte[] pdf = getGamePdfUseCase.execute();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("roosid-game.pdf").build());
        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}
