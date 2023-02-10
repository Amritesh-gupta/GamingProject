package services;

import config.Config;
import models.Game;
import models.TeamGame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TeamService {
    Connection con;

    public TeamService(){
    }

    public TeamService(Connection con){
        this.con = con;
    }

    public void addTeamGame(TeamGame teamGame) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO Teams (GameID,Name) " + "VALUES ('%d','%s');";
        String execute = String.format(sql,teamGame.getGameID(),teamGame.getTeamName());
        stmt.execute(execute);
    }

    public void addAllTeamGames(ArrayList<TeamGame> games) {
        games.forEach(teamGame -> {
            try {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO Teams (GameID,Name) " + "VALUES ('%d','%s');";
                String execute = String.format(sql,teamGame.getGameID(),teamGame.getTeamName());
                stmt.execute(execute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public TeamGame getTeamGame(int TeamID) {
        TeamGame teamGame = new TeamGame();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Teams where TeamID = '" + TeamID + "';");
            rs.next();
            teamGame.setTeamID(rs.getInt(1));
            teamGame.setGameID(rs.getInt(2));
            teamGame.setTeamName(rs.getString(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teamGame;
    }

    public ArrayList<TeamGame> getAllTeams(){
        ArrayList<TeamGame> teamGames = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Teams ;");
            while (rs.next()){
                TeamGame teamGame = new TeamGame();

                teamGame.setTeamID(rs.getInt(1));
                teamGame.setGameID(rs.getInt(2));
                teamGame.setTeamName(rs.getString(3));
                teamGames.add(teamGame);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teamGames;
    }

}
