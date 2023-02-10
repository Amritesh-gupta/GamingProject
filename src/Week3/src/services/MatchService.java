package services;

import config.Config;
import models.Match;
import models.TeamGame;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MatchService {
    Connection con;

    public MatchService(){

    }

    public MatchService(Connection con){
        this.con = con;
    }

    public void addMatch(Match match) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO Matches (MatchID,TeamA_ID,TeamB_ID,Winner_ID) " + "VALUES ('%d','%d','%d','%d');";
        String execute = String.format(sql,match.getMatchID(),match.getTeamA_ID(),match.getTeamB_ID(),match.getWinnerTeamID());
        stmt.execute(execute);
    }

    public void addMatchID(int matchID) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO Matches (MatchID) " + "VALUES ('%d');";
        String execute = String.format(sql,matchID);
        stmt.execute(execute);
    }

    public void addAllMatches(ArrayList<Match> matches) {
        matches.forEach(match -> {
            try {
                Statement stmt=con.createStatement();
                String sql = "INSERT INTO Matches (TeamA_ID,TeamB_ID,Winner_ID) " + "VALUES ('%d','%d','%d');";
                String execute = String.format(sql,match.getTeamA_ID(),match.getTeamB_ID(),match.getWinnerTeamID());
                stmt.execute(execute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addTeamsIDsToMatch(int matchID,int teamA_ID,int teamB_ID) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "UPDATE Matches set TeamA_ID = '%d', TeamB_ID = '%d' where MatchID = '%d'";
        String execute = String.format(sql,teamA_ID,teamB_ID,matchID);
        stmt.execute(execute);
    }

    public void addWinnerIDToMatch(int matchID,int winnerID) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "UPDATE Matches set Winner_ID = '%d' where MatchID = '%d'";
        String execute = String.format(sql,winnerID,matchID);
        stmt.execute(execute);
    }

    public Match getMatch(int matchID) {
        Match match = new Match();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Matches where MatchID = '" + matchID + "';");
            rs.next();
            match.setMatchID(rs.getInt(1));
            match.setTeamA_ID(rs.getInt(2));
            match.setTeamB_ID(rs.getInt(3));
            match.setWinnerTeamID(rs.getInt(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return match;
    }

    public Integer getLastMatchID() {
        ResultSet rs;
        Integer lastMatchID;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM Matches ORDER BY MatchID DESC LIMIT 1");
            if (!rs.isBeforeFirst() ) {
                return null;
            }
            rs.next();

            lastMatchID =  rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lastMatchID;
    }
}
