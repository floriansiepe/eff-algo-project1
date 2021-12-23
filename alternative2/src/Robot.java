public class Robot {
    String name;
    int costM;
    int costF;
    int shoeSize;
    String fFood;
    String special;
    int valueM;
    int valueF;

    Robot (String name, int costM, int costF, int shoeSize, String fFood, String special){
        this.name = name;
        this.costM = costM;
        this.costF = costF;
        this.fFood = fFood;
        this.shoeSize = shoeSize;
        this.special = special;
    }

    public void setValueM(int valueM) {
        this.valueM = valueM;
    }

    public void setValueF(int valueF) {
        this.valueF = valueF;
    }

    public int getValueF() {
        return valueF;
    }

    public int getValueM() {
        return valueM;
    }
}
