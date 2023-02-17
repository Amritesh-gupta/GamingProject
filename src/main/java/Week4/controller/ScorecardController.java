package Week4.controller;

import Week4.model.BatsmanStat;
import Week4.model.BowlerStat;
import Week4.service.BatsmanScorecardService;
import Week4.service.BowlerScorecardService;
import Week4.service.Impl.BatsmanScorecardServiceImpl;
import Week4.service.Impl.BowlerScorecardServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ScorecardController {

    private BatsmanScorecardService batsmanScorecardService;
    private BowlerScorecardService bowlerScorecardService;

    public ScorecardController(BatsmanScorecardServiceImpl batsmanScorecardService, BowlerScorecardServiceImpl bowlerScorecardService) {
        this.batsmanScorecardService = batsmanScorecardService;
        this.bowlerScorecardService = bowlerScorecardService;
    }

    @GetMapping("/batsamanScorecardByMatch")
    public ArrayList<BatsmanStat> getBatsmanScorecard(@RequestParam int matchID){
        return batsmanScorecardService.getAllBatsmanStat(matchID);
    }

    @GetMapping("/bowlerScorecardByMatch")
    public ArrayList<BowlerStat> getBowlerScorecard(@RequestParam int matchID){
        return bowlerScorecardService.getAllBowlerStat(matchID);
    }

    @GetMapping("/batsamanScorecardByMatchAndPlayer")
    public BatsmanStat getBatsmanScorecard(@RequestParam int matchID,@RequestParam int playerID){
        return batsmanScorecardService.getBatsmanStat(matchID,playerID);
    }

    @GetMapping("/bowlerScorecardByMatchAndPlayer")
    public BowlerStat getBowlerScorecard(@RequestParam int matchID,@RequestParam int playerID){
        return bowlerScorecardService.getBowlerStat(matchID,playerID);
    }
}
