package services;

import config.Config;
import models.Player;

import java.sql.*;
import java.util.ArrayList;

public class PlayerService {
    Connection con;

    public PlayerService(){

    }

    public PlayerService(Connection con){
        this.con = con;
    }

    public void addPlayer(Player player) throws SQLException {
        Statement stmt=con.createStatement();
        String sql = "INSERT INTO Players (Name,Role,Age,Country,Game) " + "VALUES ('%s','%s','%d','%s','%s');";
        String execute = String.format(sql,player.getName(),player.getRole(),player.getAge(),
                player.getBelongsToCountry(),player.getGame());
        stmt.execute(execute);
    }

    public void addAllPlayers(ArrayList<? extends Player> players) {
        players.forEach(player -> {
            try {
                Statement stmt = con.createStatement();
                String sql = "INSERT INTO Players (PlayerID,Name,Role,Age,Country,Game) " + "VALUES ('%d','%s','%s','%d','%s','%s');";
                String execute = String.format(sql,player.getPlayerID(),player.getName(),player.getRole(),player.getAge(),
                        player.getBelongsToCountry(),player.getGame());
                stmt.execute(execute);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Player getPlayer(int playerID) {
        Player player;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Players where PlayerID = '" + playerID + "';");
            rs.next();
            player = new Player() {
                @Override
                public void hobbies() {
                }
            };
            player.setPlayerID(rs.getInt(1));
            player.setName(rs.getString(2));
            player.setRole(rs.getString(3));
            player.setAge(rs.getInt(4));
            player.setBelongsToCountry(rs.getString(5));
            player.setGame(rs.getString(6));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return player;
    }

    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> players = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Players;");
            while(rs.next()) {
                Player player = new Player() {
                    @Override
                    public void hobbies() {
                    }
                };
                player.setPlayerID(rs.getInt(1));
                player.setName(rs.getString(2));
                player.setRole(rs.getString(3));
                player.setAge(rs.getInt(4));
                player.setBelongsToCountry(rs.getString(5));
                player.setGame(rs.getString(6));
                players.add(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }

    public Player getPlayerById(int playerID){
        Player player;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Players where PlayerID = '" + playerID + "';");

            player = new Player() {
                @Override
                public void hobbies() {
                }
            };
            rs.next();
            player.setPlayerID(rs.getInt(1));
            player.setName(rs.getString(2));
            player.setRole(rs.getString(3));
            player.setAge(rs.getInt(4));
            player.setBelongsToCountry(rs.getString(5));
            player.setGame(rs.getString(6));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return player;
    }

    public ArrayList<Player> getAllPlayersByCountryAndGame(String country,String game){
        ArrayList<Player> players = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Players where Country = '" + country + "' and '"+game +"';");
            while(rs.next()) {
                Player player = new Player() {
                    @Override
                    public void hobbies() {
                    }
                };
                player.setPlayerID(rs.getInt(1));
                player.setName(rs.getString(2));
                player.setRole(rs.getString(3));
                player.setAge(rs.getInt(4));
                player.setBelongsToCountry(rs.getString(5));
                player.setGame(rs.getString(6));
                players.add(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }

    public ArrayList<Player> getAllPlayersByGame(String game){
        ArrayList<Player> players = new ArrayList<>();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from Players where Game = '" +game +"';");
            while(rs.next()) {
                Player player = new Player() {
                    @Override
                    public void hobbies() {
                    }
                };
                player.setPlayerID(rs.getInt(1));
                player.setName(rs.getString(2));
                player.setRole(rs.getString(3));
                player.setAge(rs.getInt(4));
                player.setBelongsToCountry(rs.getString(5));
                player.setGame(rs.getString(6));
                players.add(player);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return players;
    }

}
