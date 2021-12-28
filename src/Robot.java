package src;

public class Robot implements Comparable<Robot>{
  String name;
  int costM;
  int costF;
  int shoeSize;
  int oppCost;
  String fFood;
  String special;

  Robot (String name, int costM, int costF, int oppCost, int shoeSize, String fFood, String special){
    this.name = name;
    this.costM = costM;
    this.costF = costF;
    this.oppCost = oppCost;
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
            ", oppCost=" +oppCost +
            '}';
  }

  @Override
  public int compareTo(Robot o) {
    return oppCost-o.oppCost;
  }
}