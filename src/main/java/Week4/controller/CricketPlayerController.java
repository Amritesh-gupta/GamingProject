package Week4.controller;

import Week4.model.CricketPlayer;
import Week4.model.Game;
import Week4.service.CricketPlayerService;
import Week4.service.Impl.CricketPlayerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CricketPlayerController {

    private CricketPlayerService cricketPlayerService;

    public CricketPlayerController(CricketPlayerServiceImpl cricketPlayerService) {
        this.cricketPlayerService = cricketPlayerService;
    }

    @PostMapping("/cricketPlayers")
    public ResponseEntity<String> addAllGames(@RequestBody ArrayList<CricketPlayer> cricketPlayers){
        cricketPlayerService.addAllPlayers(cricketPlayers);
        return new ResponseEntity<>("Successfully uploaded", HttpStatus.OK);
    }
}
