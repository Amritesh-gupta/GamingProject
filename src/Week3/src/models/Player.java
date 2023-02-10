package models;

public abstract class Player {

    private int playerID;
    private String name;
    private int age;
    private String role;

    private String belongsToCountry;

    private String game;

    public Player(int playerID,String name, int age, String role, String belongsToCountry,String game) {
        this.playerID = playerID;
        this.name = name;
        this.age = age;
        this.role = role;
        this.belongsToCountry = belongsToCountry;
        this.game = game;
    }

    public Player(){

    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getBelongsToCountry() {
        return belongsToCountry;
    }

    public void setBelongsToCountry(String belongsToCountry) {
        this.belongsToCountry = belongsToCountry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public abstract void hobbies();
}
