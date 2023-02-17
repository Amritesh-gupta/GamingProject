package Week4.controller;

import Week4.model.TeamStat;
import Week4.service.Impl.TeamScorecardServiceImpl;
import Week4.service.TeamScorecardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TeamScorecardController {

    private TeamScorecardService teamScorecardService;

    public TeamScorecardController(TeamScorecardServiceImpl teamScorecardService) {
        this.teamScorecardService = teamScorecardService;
    }


    @GetMapping("/teamScorecardByMatch")
    public ArrayList<TeamStat> getTeamScorecardByMatch(@RequestParam int matchID){
        return teamScorecardService.getAllTeamsStatByMatch(matchID);
    }

    @GetMapping("/teamScorecardByMatchAndTeam")
    public TeamStat getTeamScorecardByMatchAndTeam(@RequestParam int matchID, @RequestParam int teamID){
        return teamScorecardService.getTeamStat(matchID,teamID);
    }
}
