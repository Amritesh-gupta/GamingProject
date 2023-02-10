package services;

import config.Config;
import models.Game;
import models.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GameService {
    Connection con;

    public GameService(){
    }

    public GameService(Connection con){
        this.con = con;
    }

    public void addGame(Game game) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO Games (Name,RequiredPlayersPerTeam) " + "VALUES ('%s','%d');";
        String execute = String.format(sql,game.getGameName(),game.getRequiredPlayersPerMatch());
        stmt.execute(execute);
    }

    public void addAllGames(ArrayList<Game> games) {
        games.forEach(game -> {
            try {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO Games (Name,RequiredPlayersPerTeam) " + "VALUES ('%s','%d');";
                String execute = String.format(sql,game.getGameName(),game.getRequiredPlayersPerMatch());
                stmt.execute(execute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Game getGame(int GameID) {
        Game game = new Game();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Games where GameID = '" + GameID + "';");
            rs.next();
            game.setGameID(rs.getInt(1));
            game.setGameName(rs.getString(2));
            game.setRequiredPlayersPerMatch(rs.getInt(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    public Game getGamebyName(String gameName){
        Game game = new Game();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Games where Name = '" + gameName + "';");
            rs.next();
            game.setGameID(rs.getInt(1));
            game.setGameName(rs.getString(2));
            game.setRequiredPlayersPerMatch(rs.getInt(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    public ArrayList<Game> getAllGames(){
        ArrayList<Game> games = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Games;");
            while(rs.next()){
                Game game = new Game();
                game.setGameID(rs.getInt(1));
                game.setGameName(rs.getString(2));
                game.setRequiredPlayersPerMatch(rs.getInt(3));
                games.add(game);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return games;
    }
}
