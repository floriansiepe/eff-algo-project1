public class Robot {
    String name;
    int costM;  //Fabrik
    int costF;  //Farm
    int shoeSize;
    String fFood;
    String special;

    Robot (String name, int costM, int costF, int shoeSize, String fFood, String special){
        this.name = name;
        this.costM = costM;
        this.costF = costF;
        this.fFood = fFood;
        this.shoeSize = shoeSize;
        this.special = special;
    }

    @Override
    public String toString() {
        return "Robot{" +
                "name='" + name + '\'' +
                ", costM=" + costM +
                ", costF=" + costF +
                '}';
    }
}
