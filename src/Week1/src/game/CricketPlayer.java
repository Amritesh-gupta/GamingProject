package game;

public class CricketPlayer extends Player{


    private int totalRunsScored;
    private int numberOfCenturies;
    private int numberOfMatchesPlayed;

    public CricketPlayer(String name, int age, String role, int totalRunsScored, int numberOfCenturies, int numberOfMatchesPlayed, double avgStrikeRate, String belongsToCountry,String gameName) {
        super(name,age,role,gameName);
        this.totalRunsScored = totalRunsScored;
        this.numberOfCenturies = numberOfCenturies;
        this.numberOfMatchesPlayed = numberOfMatchesPlayed;
        this.avgStrikeRate = avgStrikeRate;
        this.belongsToCountry = belongsToCountry;
    }

    public CricketPlayer(){

    }

    private double avgStrikeRate;

    private String belongsToCountry;

    public String getBelongsToCountry() {
        return belongsToCountry;
    }

    public void setBelongsToCountry(String belongsToCountry) {
        this.belongsToCountry = belongsToCountry;
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
        System.out.println("Playing cricket");
    }




}
