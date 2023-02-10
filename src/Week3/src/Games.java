import models.Team;

public interface Games {
    public void play(Team teamA, Team teamB);
    void rules();
    void initializeGame();
}
