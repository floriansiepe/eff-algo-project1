import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

/**
 * Generally add exceptions to everything
 */
public class Optimize {

    public static void main(String[] args) throws IOException {
        Optimize test = new Optimize();
        test.changeSalesM(1800);
        test.changeSalesF(740);
        test.addRobot("/Users/joshuakuhwald/IdeaProjects/Miniprojekt/src/Roboterliste.csv");
        test.print();
        System.out.println(test.location.get("Olaf"));
        test.optimize();
        test.print();
    }


    /*List<Robot> allM = new LinkedList<>();
    List<Robot> allF = new LinkedList<>();*/
    HashMap<Robot, Pair> value = new HashMap<Robot, Pair>();
    List<Robot> rim = new LinkedList<>();
    List<Robot> rif = new LinkedList<>();
    HashMap<String, Boolean> location = new HashMap<>();
    int salesM;
    int salesF;
    int profit;


    public void addRobot (String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String nextLine = br.readLine();
        profit = 0;
        while (nextLine != null){
            String[] data = nextLine.split(",");
            Robot tmp = new Robot(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4], data[5]);
            tmp.setValueM(salesM-tmp.costM);
            tmp.setValueF(salesF-tmp.costF);
            value.put(tmp, new Pair(tmp.valueM, tmp.valueF));
            if (tmp.special.equals("bekommt leicht eine Erkaeltung")){
                rim.add(tmp);
                location.put(tmp.name, true);
            }
            else {
                if ((profit + tmp.valueM - updateClimate(rim.size()+1)) > (profit + tmp.valueF - updateClimate(rim.size()))){
                    rim.add(tmp);
                    location.put(tmp.name, true);
                    profit += tmp.valueM;
                }
                else {
                    rif.add(tmp);
                    location.put(tmp.name, false);
                    profit += tmp.valueF;
                }
            }
            nextLine = br.readLine();
        }
    }

    /*
    public void addRobot (String name, int costM, int costF, int shoeSize, String fFood, String special){
        allM.add(new Robot(name, costM, costF, shoeSize, fFood, special));
        optimize();
    }*/

    public void optimize(){
        rim.sort(new Comparator<Robot>() {
            @Override
            public int compare(Robot o1, Robot o2) {
                return o1.valueM - o2.valueM;
            }
        });
        rif.sort(new Comparator<Robot>() {
            @Override
            public int compare(Robot o1, Robot o2) {
                return o1.valueF-o2.valueF;
            }
        });

        for (Robot r : rim) {
            if (r.special.equals("bekommt leicht eine Erkaeltung")) {
            } else {
                if ((profit + r.valueM - updateClimate(rim.size())) > (profit + r.valueF - updateClimate(rim.size()-1))) {
                } else {
                    rif.add(r);
                    rim.removeIf(x -> x.name == r.name);
                    location.put(r.name, false);
                    profit -= r.valueM;
                    profit += r.valueF;
                }
            }
        }
        for (Robot r : rif) {
            if (r.special.equals("bekommt leicht eine Erkaeltung")) {
            } else {
                if ((profit + r.valueM - updateClimate(rim.size() + 1)) > (profit + r.valueF - updateClimate(rim.size()))) {
                    rim.add(r);
                    rif.removeIf(x -> x.name == r.name);
                    location.put(r.name, true);
                    profit -= r.valueF;
                    profit += r.valueM;
                }
            }
        }

    }


    public void print(){
        System.out.println(profit-updateClimate(rim.size()));
        System.out.println(rim.size());
        System.out.println(rif.size());
/*
        for (Robot r : rim) {
            System.out.println(r.name + " | " + r.valueM);
        }
*/
    }

    public void changeSalesM (int sales){
        this.salesM = sales;
    }

    public void changeSalesF (int sales){
        this.salesF = sales;
    }

    /*
    public void removeRobot (String name){
        allM.removeIf(r -> r.name.equals(name));
        //remove from other data structures as applicable
    }*/

    public int updateClimate(int n){
        BinCo bin = new BinCo();
        return bin.binCof(n, 2);
    }

}
