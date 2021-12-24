import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
* RoboterTest.csv output 1086
*
*
*
* */

public class OptimizeIncome {
    List<Robot> robots;
    int countManufacture = 0;
    /* difference needed for check what is better to choose between manufacture with vent or farm   */
    public double difference(double a, double b){
        if(!(a<0)&&!(b<0)){
            return Math.max(a,b)-Math.min(a,b);
        }else{
            return Math.min(a,b)*-1+Math.max(a,b);
        }
    }

    public void initialize(String fileName){
        robots = new ArrayList<>();
        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                if(counter == 0){
                    counter++;
                }else{
                    String[] values = line.split(",");
                    var manInt = Integer.parseInt(values[1]);
                    var farmInt = Integer.parseInt(values[2]);

                    var robot = new Robot(values[0],manInt,farmInt,Integer.parseInt(values[3]),values[4],values[5],"",0);
                    robots.add(robot);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double optInc(double umsatzFabrik,double umsatzFarm){

        double income = 0.0;
        /*
        * add place and difference to Robot with variable costs
        *  */
        for(var robot:robots){
            var fabrik = umsatzFabrik-robot.costM;
            var farm = umsatzFarm - robot.costF;
            if(robot.special.equals("bekommt leicht eine Erkaeltung")){
                robot.place = "manufacture";
                robot.difference = difference(fabrik,farm);
                countManufacture++;
            }else{
                if((umsatzFabrik-robot.costM)>umsatzFarm-robot.costF){
                    robot.place = "manufacture";
                    robot.difference = difference(fabrik,farm);
                    countManufacture++;
                }else{
                    robot.place = "farm";
                    robot.difference = difference(fabrik,farm);;
                }
            }
        }

        robots.sort((a,b)->Double.compare(a.difference,b.difference));
        /* change Robot place if alternative without vent is better */
        var venting = 0.0;
        for(var robot:robots){
            venting = countBinomialKoeffizient(countManufacture,2) - countBinomialKoeffizient(countManufacture-1,2);
            if(robot.special.equals("bekommt leicht eine Erkaeltung")){
                income += umsatzFabrik-robot.costM;
            }else {
                if(robot.place.equals("manufacture")){
                    if(robot.difference<venting){
                        robot.place = "farm";
                        income += umsatzFarm - robot.costF;
                        countManufacture--;
                    }else{
                        income += umsatzFabrik - robot.costM;
                    }
                }else{
                    income += umsatzFarm - robot.costF;
                }
            }


        }
        venting = countBinomialKoeffizient(countManufacture,2);
        return income - venting;
    }

    public long countBinomialKoeffizient(int n, int k) {
        if (n<k) return 0;
        if (n<2*k) k = n-k;
        if (k==1) return n;
        if (k == 0) return 1;
        else return countBinomialKoeffizient(n-1,k-1) + countBinomialKoeffizient(n-1,k);
    }




    public static void main(String[] args){
        var optInc = new OptimizeIncome();
        optInc.initialize("RoboterTest.csv");
        System.out.println(optInc.optInc(1800,740));
        /*
        * expected 1086 returned 1086
        *
        * */



    }
}
