package Week4.controller;

import Week4.model.Game;
import Week4.model.Team;
import Week4.service.TeamService;
import Week4.service.Impl.TeamServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class TeamController {
    private TeamService teamService;

    public TeamController(TeamServiceImpl teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/teams")
    public ResponseEntity<String> addAllTeams(@RequestBody ArrayList<Team> teams){
        teamService.addAllTeamGames(teams);
        return new ResponseEntity<>("Successfully uploaded", HttpStatus.OK);
    }


}
