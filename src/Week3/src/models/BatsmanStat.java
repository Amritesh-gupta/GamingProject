package models;

public class BatsmanStat {

    private int runs;
    private int ballsPlayed;
    private int numberOfSixes;
    private int numberOfFours;

    public BatsmanStat(int runs, int ballsPlayed, int numberOfSixes, int numberOfFours) {
        this.runs = runs;
        this.ballsPlayed = ballsPlayed;
        this.numberOfSixes = numberOfSixes;
        this.numberOfFours = numberOfFours;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public int getNumberOfSixes() {
        return numberOfSixes;
    }

    public void setNumberOfSixes(int numberOfSixes) {
        this.numberOfSixes = numberOfSixes;
    }

    public int getNumberOfFours() {
        return numberOfFours;
    }

    public void setNumberOfFours(int numberOfFours) {
        this.numberOfFours = numberOfFours;
    }

    public void displayStat() {
        System.out.println(runs + "\t" + ballsPlayed + "\t" + numberOfFours + "\t" + numberOfSixes);
    }

    @Override
    public String toString() {
        return "BatsmanStat{" +
                "runs=" + runs +
                ", ballsPlayed=" + ballsPlayed +
                ", numberOfSixes=" + numberOfSixes +
                ", numberOfFours=" + numberOfFours +
                '}';
    }
}
