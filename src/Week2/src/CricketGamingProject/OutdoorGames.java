package CricketGamingProject;

public abstract class OutdoorGames implements Games{


    public abstract void play(Team teamA, Team teamB);


    @Override
    public abstract void rules();
    public abstract void initializeGame();

    public void toadayWeatherCondition(){
        // Just for testing purpose
        System.out.println("Nice and Warm");
    }

    public abstract void matchLocation();

}
