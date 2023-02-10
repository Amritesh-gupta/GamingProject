package models;

public class Game {

    private int gameID;
    private String gameName;
    private int requiredPlayersPerMatch;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getRequiredPlayersPerMatch() {
        return requiredPlayersPerMatch;
    }

    public void setRequiredPlayersPerMatch(int requiredPlayersPerMatch) {
        this.requiredPlayersPerMatch = requiredPlayersPerMatch;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameID=" + gameID +
                ", gameName='" + gameName + '\'' +
                ", requiredPlayersPerMatch=" + requiredPlayersPerMatch +
                '}';
    }
}
