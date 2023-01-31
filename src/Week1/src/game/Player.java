package game;

public abstract class Player {
    private String name;
    private int age;
    private String role;

    private String gameName;

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Player(String name, int age, String role, String gameName) {
        this.name = name;
        this.age = age;
        this.role = role;
        this.gameName = gameName;
    }

    public Player(){

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
