package Week4.controller;

import Week4.service.CricketGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CricketGameController {
    private CricketGameService cricketGameService;

    public CricketGameController(CricketGameService cricketGameService) {
        this.cricketGameService = cricketGameService;
    }

    @PostMapping("/startGame")
    public ResponseEntity<String> startCricketGame(@RequestParam int overs){
        cricketGameService.startGame(overs,null);
        return new ResponseEntity<>("Successfully completed match", HttpStatus.OK);
    }

}
