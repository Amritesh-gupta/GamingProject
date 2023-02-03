package CricketGamingProject;

public abstract class Player {
    private String name;
    private int age;
    private String role;

    private String belongsToCountry;



    public Player(String name, int age, String role, String belongsToCountry) {
        this.name = name;
        this.age = age;
        this.role = role;
        this.belongsToCountry = belongsToCountry;
    }

    public Player(){

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
