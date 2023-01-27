package cricketPlayers;

import game.CricketPlayer;

public class WashingtonSundar extends CricketPlayer {
    private int totalRunsScored;
    private int numberOfCenturies;
    private int numberOfMatchesPlayed;
    private double avgStrikeRate;

    public WashingtonSundar(){
        setName("Washington Sundar");
        setAge(35);
        setRole("All rouder");
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
        return "India";
    }
}
