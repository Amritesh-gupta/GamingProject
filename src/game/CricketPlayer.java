package game;

public abstract class CricketPlayer extends Player{


    @Override
    public abstract void hobbies();
    public abstract String belongsToCountry();

    @Override
    public String getGameName() {
        return "Cricket";
    }


}
