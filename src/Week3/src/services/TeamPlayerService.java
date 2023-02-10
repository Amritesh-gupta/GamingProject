package services;

import config.Config;
import models.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TeamPlayerService {
    Connection con;

    public TeamPlayerService(){

    }

    public TeamPlayerService(Connection con){
        this.con = con;
    }

    public void addTeamPlayer(int teamID,int playerID) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO TeamPlayers (TeamID,PlayerID) " + "VALUES ('%d','%d');";
        String execute = String.format(sql,teamID,playerID);
        stmt.execute(execute);
    }

    public void addAllTeamPlayersIDs(int teamID, ArrayList<Integer> playersIDs) {
        playersIDs.forEach(playerID -> {
            try {
                Statement stmt=con.createStatement();
                String sql = "INSERT INTO TeamPlayers (TeamID,PlayerID) " + "VALUES ('%d','%d');";
                String execute = String.format(sql,teamID,playerID);
                stmt.execute(execute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addAllTeamPlayers(int teamID, ArrayList<Player> players) {
        players.forEach(player -> {
            try {
                int playerID = player.getPlayerID();
                Statement stmt=con.createStatement();
                String sql = "INSERT INTO TeamPlayers (TeamID,PlayerID) " + "VALUES ('%d','%d');";
                String execute = String.format(sql,teamID,playerID);
                stmt.execute(execute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int getPlayerTeamID(int playerID) {
        int teamID;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from TeamPlayers where PlayerID = '" + playerID + "';");
            rs.next();
            teamID = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teamID;
    }

    public ArrayList<Integer> getAllTeamPlayerIDs(int teamID) {
        ArrayList<Integer> playerIDs = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from TeamPlayers where TeamID = '" + teamID + "';");
            while(rs.next()){
                int playerID = rs.getInt(2);
                playerIDs.add(playerID);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return playerIDs;
    }


}
