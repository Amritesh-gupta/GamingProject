package Week4.enums;

public enum PossibleOutcomesOfBall {
    DOTBALL("0",0), ONERUN("1",1), TWORUNS("2",2), THREERUNS("3",3), FOUR("4",4), FIVERUNS("5",5), SIX("6",6)
    ,NOBALL("NB",7), WIDEBALL("WB",8), WICKET("W",9);


    private final String value;
    private final int index;

    private PossibleOutcomesOfBall(String value, int index){
        this.value = value;
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }
}
