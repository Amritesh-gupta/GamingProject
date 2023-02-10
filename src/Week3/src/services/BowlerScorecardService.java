package services;

import config.Config;
import models.BatsmanStat;
import models.BowlerStat;
import models.CricketPlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class BowlerScorecardService {
    Connection con;
    public BowlerScorecardService(){

    }

    public BowlerScorecardService(Connection con){
        this.con = con;
    }

    public void addBowlerStat(int matchID, int playerID, BowlerStat bowlerStat) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO BowlerScoreCard (PlayerID,MatchID,Overs,MaidenOvers,Runs,Wickets) "
                + "VALUES ('%d','%d','%f','%d','%d','%d');";
        String execute = String.format(sql,playerID,matchID,bowlerStat.getNumberOfOvers(),bowlerStat.getNumberOfMaidenOvers(),
                bowlerStat.getRunsCost(),bowlerStat.getNumberOfWickets());
        stmt.execute(execute);
    }

    public void updateBowlerStat(int matchID,int playerID,BowlerStat bowlerStat) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "Update BowlerScoreCard set Overs = '%f', MaidenOvers = '%d', Runs = '%d', Wickets = '%d'" +
                " where MatchID = '%d' and PlayerID = '%d'";
        String execute = String.format(sql,bowlerStat.getNumberOfOvers(),bowlerStat.getNumberOfMaidenOvers(),
                bowlerStat.getRunsCost(),bowlerStat.getNumberOfWickets(),matchID,playerID);
        stmt.execute(execute);
    }

    public BowlerStat getBowlerStat(int matchID, int playerID){
        BowlerStat bowlerStat =  new BowlerStat(0,0,0,0);
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from BowlerScoreCard where MatchID = '" + matchID + "' and PlayerID = '" +
                    playerID + "';");
            if (!rs.isBeforeFirst() ) {
                return null;
            }
            rs.next();
            bowlerStat.setNumberOfOvers(rs.getInt(3));
            bowlerStat.setNumberOfMaidenOvers(rs.getInt(4));
            bowlerStat.setRunsCost(rs.getInt(5));
            bowlerStat.setNumberOfWickets(rs.getInt(6));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bowlerStat;
    }

    public LinkedHashMap<CricketPlayer,BowlerStat> getMapOfBowlerStat(int matchID, int teamID){
        LinkedHashMap<CricketPlayer,BowlerStat> bowlerScorecard = new LinkedHashMap<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Players join BowlerScoreCard on BowlerScoreCard.PlayerID = Players.PlayerID " +
                    "join TeamPlayers on TeamPlayers.PlayerID = BowlerScoreCard.PlayerID\n" +
                    "where MatchID = " +matchID+ " and TeamID = " +teamID+";");

            while(rs.next()){
                CricketPlayer cricketPlayer = new CricketPlayer();
                cricketPlayer.setName(rs.getString(2));
                cricketPlayer.setRole(rs.getString(3));
                cricketPlayer.setAge(rs.getInt(4));
                cricketPlayer.setBelongsToCountry(rs.getString(5));
                cricketPlayer.setGame(rs.getString(6));

                BowlerStat bowlerStat = new BowlerStat(0,0,0,0);
                bowlerStat.setNumberOfOvers(rs.getDouble(9));
                bowlerStat.setNumberOfMaidenOvers(rs.getInt(10));
                bowlerStat.setRunsCost(rs.getInt(11));
                bowlerStat.setNumberOfWickets(rs.getInt(12));

                bowlerScorecard.put(cricketPlayer,bowlerStat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bowlerScorecard;
    }
}
