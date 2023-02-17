package Week4.controller;

import Week4.model.Player;
import Week4.service.Impl.PlayerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PlayerController {


    private PlayerServiceImpl playerService;

    public PlayerController(PlayerServiceImpl playerService) {
        this.playerService = playerService;
    }


    @PostMapping("/players")
    public ResponseEntity<String> addAllPlayers(@RequestBody ArrayList<Player> players){
        playerService.addAllPlayers(players);
        return new ResponseEntity<>("Successfully uploaded", HttpStatus.OK);
    }
}
