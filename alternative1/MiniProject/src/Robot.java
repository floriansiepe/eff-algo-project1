public class Robot {
    String name;
    int costM;
    int costF;
    int shoeSize;
    String fFood;
    String special;
    String place;
    double difference;

    @Override
    public String toString() {
        return "Robot{" +
                "name='" + name + '\'' +
                ", costM=" + costM +
                ", costF=" + costF +
                ", shoeSize=" + shoeSize +
                ", fFood='" + fFood + '\'' +
                ", special='" + special + '\'' +
                ", place='" + place + '\'' +
                ", difference=" + difference +
                '}';
    }

    Robot (String name, int costM, int costF, int shoeSize, String fFood, String special, String place, double difference){
        this.name = name;
        this.costM = costM;
        this.costF = costF;
        this.fFood = fFood;
        this.shoeSize = shoeSize;
        this.special = special;
        this.place = place;
        this.difference = difference;
    }



}
