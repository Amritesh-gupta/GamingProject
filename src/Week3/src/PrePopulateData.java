import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import models.*;
import services.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrePopulateData {
    public static void main(String[] args) throws IOException, SQLException {

        Connection con = Config.getConnection();

        PrePopulateData prePopulateData = new PrePopulateData();
        prePopulateData.populatePlayersTable(con);
        //prePopulateData.populateGamesTable(con);
        //prePopulateData.populateTeamsTable(con);
        //prePopulateData.populateTeamsPlayerTable(con);
        //prePopulateData.populateCricketPlayersTable(con);
    }

    public void populatePlayersTable(Connection con) throws IOException {
        PlayerService playerService = new PlayerService(con);

        File file = new File("src/Week3/src/Players.json");
        ObjectMapper objectMapper = new ObjectMapper();
        CricketTeam cricketTeam = objectMapper.readValue(file, CricketTeam.class);

        playerService.addAllPlayers(cricketTeam.getPlayers());
    }

    public void populateGamesTable(Connection con) throws SQLException {
        GameService gameService = new GameService();
        Game game = new Game();
        game.setGameName("Cricket");
        game.setRequiredPlayersPerMatch(11);
        gameService.addGame(game);
    }

    public void populateTeamsTable(Connection con) {
        TeamService teamService = new TeamService(con);
        GameService gameService = new GameService(con);
        TeamGame cricketTeam1 = new TeamGame();
        TeamGame cricketTeam2 = new TeamGame();

        int gameID = gameService.getGamebyName("Cricket").getGameID();

        cricketTeam1.setGameID(gameID);
        cricketTeam1.setTeamName("India");

        cricketTeam2.setGameID(gameID);
        cricketTeam2.setTeamName("Pakistan");

        try {
            teamService.addTeamGame(cricketTeam1);
            teamService.addTeamGame(cricketTeam2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void populateTeamsPlayerTable(Connection con) {
        TeamService teamService = new TeamService(con);
        PlayerService playerService = new PlayerService(con);
        TeamPlayerService teamPlayerService = new TeamPlayerService(con);

        ArrayList<TeamGame> teams = teamService.getAllTeams();

        teams.forEach(team -> {
            ArrayList<Player> players = playerService.getAllPlayersByCountryAndGame(team.getTeamName(),"Cricket");
            teamPlayerService.addAllTeamPlayers(team.getTeamID(),players);
        });
    }
    public void populateCricketPlayersTable(Connection con) throws IOException {
        CricketPlayerService cricketPlayerService = new CricketPlayerService(con);

        File file = new File("src/Week3/src/Players.json");
        ObjectMapper objectMapper = new ObjectMapper();
        CricketTeam cricketTeam = objectMapper.readValue(file, CricketTeam.class);

        cricketPlayerService.addAllPlayers((ArrayList<CricketPlayer>) cricketTeam.getPlayers());
    }

}
