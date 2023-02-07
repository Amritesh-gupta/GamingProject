package CricketGamingProject;

public class CricketPlayer extends Player {


    private int totalRunsScored;
    private int numberOfCenturies;
    private int numberOfMatchesPlayed;

    private double avgStrikeRate;


    public static class Builder{
        private int totalRunsScored;
        private int numberOfCenturies;
        private int numberOfMatchesPlayed;

        private double avgStrikeRate;

        private String name;
        private int age;
        private String role;

        private String belongsToCountry;

        private Builder(){}

        public static Builder newInstance(){
            return new Builder();
        }

        public Builder setTotalRunsScored(int totalRunsScored) {
            this.totalRunsScored = totalRunsScored;
            return this;
        }

        public Builder setNumberOfCenturies(int numberOfCenturies) {
            this.numberOfCenturies = numberOfCenturies;
            return this;
        }

        public Builder setNumberOfMatchesPlayed(int numberOfMatchesPlayed) {
            this.numberOfMatchesPlayed = numberOfMatchesPlayed;
            return this;
        }

        public Builder setAvgStrikeRate(double avgStrikeRate) {
            this.avgStrikeRate = avgStrikeRate;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public Builder setBelongsToCountry(String belongsToCountry) {
            this.belongsToCountry = belongsToCountry;
            return this;
        }

        public CricketPlayer build(){
            return new CricketPlayer(this);
        }
    }



    public CricketPlayer(Builder builder) {
        super(builder.name, builder.age, builder.role, builder.belongsToCountry);
        this.totalRunsScored = builder.totalRunsScored;
        this.numberOfCenturies = builder.numberOfCenturies;
        this.numberOfMatchesPlayed = builder.numberOfMatchesPlayed;
        this.avgStrikeRate = builder.avgStrikeRate;
    }



    public CricketPlayer(){

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
