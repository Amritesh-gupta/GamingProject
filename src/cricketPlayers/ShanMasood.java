package cricketPlayers;

import game.CricketPlayer;

public class ShanMasood extends CricketPlayer {
    private int totalRunsScored;
    private int numberOfCenturies;
    private int numberOfMatchesPlayed;
    private double avgStrikeRate;

    public ShanMasood(){
        setName("Shan Masood");
        setAge(35);
        setRole("Batter");
    }


    public int getTotalRunsScored() {
        return totalRunsScored;
    }

    public void setTotalRunsScored(int totalRunsScored) {
        this.totalRunsScored = totalRunsScored;
    }

    public int getNumberOfCenturies() {
        return numberOfCenturies;
    }

    public void setNumberOfCenturies(int numberOfCenturies) {
        this.numberOfCenturies = numberOfCenturies;
    }

    public int getNumberOfMatchesPlayed() {
        return numberOfMatchesPlayed;
    }

    public void setNumberOfMatchesPlayed(int numberOfMatchesPlayed) {
        this.numberOfMatchesPlayed = numberOfMatchesPlayed;
    }

    public double getAvgStrikeRate() {
        return avgStrikeRate;
    }

    public void setAvgStrikeRate(double avgStrikeRate) {
        this.avgStrikeRate = avgStrikeRate;
    }

    @Override
    public void hobbies() {
        System.out.println("Not yet decided");
    }

    @Override
    public String belongsToCountry() {
        return "Pakistan";
    }
}
