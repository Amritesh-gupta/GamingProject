package CricketGamingProject;

public enum Probability {
    HIGH(3), MODERATE(2), LOW(1);

    private final int value;

    public int getValue() {
        return value;
    }

    private Probability(int value) {
        this.value = value;
    }
}
