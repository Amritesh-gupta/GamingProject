package CricketGamingProjectWithSpringApplication;

public abstract class OutdoorGames implements Games{

    @Override
    public abstract void play();

    @Override
    public abstract void rules();
    public abstract void initializeGame();

    public void toadayWeatherCondition(){
        // Just for testing purpose
        System.out.println("Nice and Warm");
    }

    public abstract void matchLocation();

}
