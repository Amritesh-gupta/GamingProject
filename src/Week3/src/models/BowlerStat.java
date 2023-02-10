package models;

public class BowlerStat {

    private double numberOfOvers;
    private int numberOfMaidenOvers;
    private int runsCost;
    private int numberOfWickets;

    public BowlerStat(double numberOfOvers, int numberOfMaidenOvers, int runsCost, int numberOfWickets) {
        this.numberOfOvers = numberOfOvers;
        this.numberOfMaidenOvers = numberOfMaidenOvers;
        this.runsCost = runsCost;
        this.numberOfWickets = numberOfWickets;
    }

    public double getNumberOfOvers() {
        return numberOfOvers;
    }

    public void setNumberOfOvers(double numberOfOvers) {
        this.numberOfOvers = numberOfOvers;
    }

    public int getNumberOfMaidenOvers() {
        return numberOfMaidenOvers;
    }

    public void setNumberOfMaidenOvers(int numberOfMaidenOvers) {
        this.numberOfMaidenOvers = numberOfMaidenOvers;
    }

    public int getRunsCost() {
        return runsCost;
    }

    public void setRunsCost(int runsCost) {
        this.runsCost = runsCost;
    }

    public int getNumberOfWickets() {
        return numberOfWickets;
    }

    public void setNumberOfWickets(int numberOfWickets) {
        this.numberOfWickets = numberOfWickets;
    }

    public void displayStat() {
        System.out.println(numberOfOvers + "\t" + numberOfMaidenOvers + "\t" + runsCost + "\t" + numberOfWickets);
    }

    @Override
    public String toString() {
        return "BowlerStat{" +
                "numberOfOvers=" + numberOfOvers +
                ", numberOfMaidenOvers=" + numberOfMaidenOvers +
                ", runsCost=" + runsCost +
                ", numberOfWickets=" + numberOfWickets +
                '}';
    }
}
