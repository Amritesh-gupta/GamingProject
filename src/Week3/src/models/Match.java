package models;

public class Match {
    private int matchID;
    private int teamA_ID;
    private int teamB_ID;
    private int winnerTeamID;

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public int getTeamA_ID() {
        return teamA_ID;
    }

    public void setTeamA_ID(int teamA_ID) {
        this.teamA_ID = teamA_ID;
    }

    public int getTeamB_ID() {
        return teamB_ID;
    }

    public void setTeamB_ID(int teamB_ID) {
        this.teamB_ID = teamB_ID;
    }

    public int getWinnerTeamID() {
        return winnerTeamID;
    }

    public void setWinnerTeamID(int winnerTeamID) {
        this.winnerTeamID = winnerTeamID;
    }
}
