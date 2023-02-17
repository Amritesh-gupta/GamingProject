package Week4.controller;

import Week4.model.Game;
import Week4.model.Player;
import Week4.service.GameService;
import Week4.service.Impl.GameServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class GameController {
    private GameService gameService;

    public GameController(GameServiceImpl gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public ResponseEntity<String> addAllGames(@RequestBody ArrayList<Game> games){
        gameService.addAllGames(games);
        return new ResponseEntity<>("Successfully uploaded", HttpStatus.OK);
    }
}
