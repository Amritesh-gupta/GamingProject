package services;

import models.CricketPlayer;
import models.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CricketPlayerService {
    Connection con;

    public CricketPlayerService(){

    }

    public CricketPlayerService(Connection con){
        this.con = con;
    }

    public void addPlayer(CricketPlayer player) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO CricketPlayers (PlayerID,TotalRunsScored,NumberOfCenturies,NumberOfMatchesPlayed,AvgStrikeRate) "
                + "VALUES ('%d','%d','%d','%d','%f');";
        String execute = String.format(sql,player.getPlayerID(),player.getTotalRunsScored(),player.getNumberOfCenturies()
                ,player.getNumberOfMatchesPlayed(),player.getAvgStrikeRate());
        stmt.execute(execute);
    }

    public void addAllPlayers(ArrayList<CricketPlayer> players) {
        players.forEach(player -> {
            try {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO CricketPlayers (PlayerID,TotalRunsScored,NumberOfCenturies,NumberOfMatchesPlayed,AvgStrikeRate) "
                        + "VALUES ('%d','%d','%d','%d','%f');";
                String execute = String.format(sql,player.getPlayerID(),player.getTotalRunsScored(),player.getNumberOfCenturies()
                ,player.getNumberOfMatchesPlayed(),player.getAvgStrikeRate());
                stmt.execute(execute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ArrayList<CricketPlayer> getAllCricketPlayers(){
        ArrayList<CricketPlayer> cricketPlayers = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Players join CricketPlayers on CricketPlayers.PlayerID = Players.PlayerID;");
            while(rs.next()) {
                CricketPlayer cricketPlayer = new CricketPlayer();

                cricketPlayer.setPlayerID(rs.getInt(1));
                cricketPlayer.setName(rs.getString(2));
                cricketPlayer.setRole(rs.getString(3));
                cricketPlayer.setAge(rs.getInt(4));
                cricketPlayer.setBelongsToCountry(rs.getString(5));
                cricketPlayer.setGame(rs.getString(6));
                cricketPlayer.setTotalRunsScored(rs.getInt(8));
                cricketPlayer.setNumberOfCenturies(rs.getInt(9));
                cricketPlayer.setNumberOfMatchesPlayed(rs.getInt(10));
                cricketPlayer.setAvgStrikeRate(rs.getInt(11));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cricketPlayers;
    }

    public ArrayList<CricketPlayer> getAllCricketPlayersByTeamID(int teamID){
        ArrayList<CricketPlayer> cricketPlayers = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Players join CricketPlayers on CricketPlayers.PlayerID = Players.PlayerID join " +
                    "TeamPlayers on CricketPlayers.PlayerID = TeamPlayers.PlayerID" +
                    " where TeamID = " + teamID + ";");
            while(rs.next()) {
                CricketPlayer cricketPlayer = new CricketPlayer();

                cricketPlayer.setPlayerID(rs.getInt(1));
                cricketPlayer.setName(rs.getString(2));
                cricketPlayer.setRole(rs.getString(3));
                cricketPlayer.setAge(rs.getInt(4));
                cricketPlayer.setBelongsToCountry(rs.getString(5));
                cricketPlayer.setGame(rs.getString(6));
                cricketPlayer.setTotalRunsScored(rs.getInt(8));
                cricketPlayer.setNumberOfCenturies(rs.getInt(9));
                cricketPlayer.setNumberOfMatchesPlayed(rs.getInt(10));
                cricketPlayer.setAvgStrikeRate(rs.getInt(11));
                cricketPlayers.add(cricketPlayer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cricketPlayers;
    }
}
