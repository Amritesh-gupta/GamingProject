package services;

import config.Config;
import models.BatsmanStat;
import models.CricketPlayer;
import models.Game;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BatsmanScorecardService {

    Connection con;
    public BatsmanScorecardService(){

    }

    public BatsmanScorecardService(Connection con){
        this.con = con;
    }

    public void addBatsmanStat(int matchID, int playerID, BatsmanStat batsmanStat) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO BatsmanScoreCard (PlayerID,MatchID,Runs,Balls,NumberOfSixes,NumberOfFours) "
                + "VALUES ('%d','%d','%d','%d','%d','%d');";
        String execute = String.format(sql,playerID,matchID,batsmanStat.getRuns(),batsmanStat.getBallsPlayed()
        ,batsmanStat.getNumberOfSixes(),batsmanStat.getNumberOfFours());
        stmt.execute(execute);
    }

    public void updateBatsmanStat(int matchID,int playerID,BatsmanStat batsmanStat) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "Update BatsmanScoreCard set Runs = '%d', Balls = '%d', NumberOfSixes = '%d',NumberOfFours = '%d'" +
                " where MatchID = '%d' and PlayerID = '%d'";
        String execute = String.format(sql,batsmanStat.getRuns(),batsmanStat.getBallsPlayed()
                ,batsmanStat.getNumberOfSixes(),batsmanStat.getNumberOfFours(),matchID,playerID);
        stmt.execute(execute);
    }

    public BatsmanStat getBatsmanStat(int matchID, int playerID){
        BatsmanStat batsmanStat =  new BatsmanStat(0,0,0,0);
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from BatsmanScoreCard where MatchID = '" + matchID + "' and PlayerID = '" +
                    playerID + "';");
            rs.next();
            batsmanStat.setRuns(rs.getInt(3));
            batsmanStat.setBallsPlayed(rs.getInt(4));
            batsmanStat.setNumberOfSixes(rs.getInt(5));
            batsmanStat.setNumberOfFours(rs.getInt(6));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batsmanStat;
    }

    public LinkedHashMap<CricketPlayer,BatsmanStat> getMapOfBatsmanStat(int matchID, int teamID){
        LinkedHashMap<CricketPlayer,BatsmanStat> batsmanScorecard = new LinkedHashMap<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Players join BatsmanScoreCard on BatsmanScoreCard.PlayerID = Players.PlayerID " +
                    "join TeamPlayers on TeamPlayers.PlayerID = BatsmanScoreCard.PlayerID\n" +
                    "where MatchID = " +matchID+ " and TeamID = " +teamID+";");

            while(rs.next()){
              CricketPlayer cricketPlayer = new CricketPlayer();
              cricketPlayer.setName(rs.getString(2));
              cricketPlayer.setRole(rs.getString(3));
              cricketPlayer.setAge(rs.getInt(4));
              cricketPlayer.setBelongsToCountry(rs.getString(5));
              cricketPlayer.setGame(rs.getString(6));

              BatsmanStat batsmanStat = new BatsmanStat(0,0,0,0);
              batsmanStat.setRuns(rs.getInt(9));
              batsmanStat.setBallsPlayed(rs.getInt(10));
              batsmanStat.setNumberOfSixes(rs.getInt(11));
              batsmanStat.setNumberOfFours(rs.getInt(12));

              batsmanScorecard.put(cricketPlayer,batsmanStat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return batsmanScorecard;
    }
}
