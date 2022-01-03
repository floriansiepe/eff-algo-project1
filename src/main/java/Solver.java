import java.io.IOException;
import java.util.*;

public class Solver {
    public static final int TURNOVER_FARM = 740;
    public static final int TURNOVER_FABRIC = 1800;
    public static final BinomialCoefficient COEFFICIENT = new BinomialCoefficient();

    public static void main(String[] args) throws IOException {
        final var inputReader = new InputReader();
        final var robots = inputReader.readFile("Roboterliste.csv");
        final var solver = new Solver();
        final var solution = solver.solve4(robots);
        System.out.println(solution);
    }
    /**
    solve 3 mit Extrawünschen
    solve 4 ohne Extrawünsche
    */

    private Solution solve(final List<Robot> robots) {
        return knapsackDP(robots);
    }

    private Solution solve3(final List<Robot> robots) {
        return linearMethod(robots);
    }

    private Solution solve4(final List<Robot> robots) {
        return linearMethod2(robots);
    }

    Solution linearMethod(List<Robot> robots) {
        int numberRobots = robots.size();
        long revenue = 0;
        List<Robot> robotsFarm = new LinkedList<>();
        List<Robot> robotsFabric = new LinkedList<>();
        if(numberRobots <= 0) {
            return new Solution();
        }
        List<Robot> sortedRobots = new ArrayList<>(robots);
        Set<Robot> mustFabric = new HashSet<>();
        //Alle Roboter, die eh mehr auf der Farm einbringen
        for(int i = 0; i < sortedRobots.size();) {
            Robot r = sortedRobots.get(i);
            if(r.special.equals("bekommt leicht eine Erkaeltung")) {
                sortedRobots.remove(i);
                mustFabric.add(r);
            } else {
                int farmRevenue = TURNOVER_FARM - r.costF;
                if(farmRevenue >= TURNOVER_FABRIC - r.costM) {
                    robotsFarm.add(r);
                    revenue += farmRevenue;
                    sortedRobots.remove(i);
                } else {
                    i++;
                }
            }
        }
        //Danach sortieren, wie viel sie mehr auf der Fabrik einbringen (aufsteigend sortiert)
        sortedRobots.sort(new Comparator<Robot>() {
            @Override
            public int compare(Robot r1, Robot r2) {
                int r1MoreRevenueFabric =  (TURNOVER_FABRIC - r1.costM) - (TURNOVER_FARM - r1.costF);
                int r2MoreRevenueFabric =  (TURNOVER_FABRIC - r2.costM) - (TURNOVER_FARM - r2.costF);
                return r1MoreRevenueFabric - r2MoreRevenueFabric;
            }
        });
        long revenueFarm = revenue;
        int revenueFabric = 0;
        long climateCosts = COEFFICIENT.binomialTo2(sortedRobots.size() + mustFabric.size());
        for(Robot r: sortedRobots) {
            revenueFabric += TURNOVER_FABRIC - r.costM;
        }
        long bestTotalRevenue = revenueFarm + revenueFabric - climateCosts;
        int bestI = -1;
        for(int i = 0; i < sortedRobots.size(); i++) {
            Robot newFarmRobot = sortedRobots.get(i);
            revenueFarm += (TURNOVER_FARM - newFarmRobot.costF);
            revenueFabric -= (TURNOVER_FABRIC - newFarmRobot.costM);
            climateCosts = COEFFICIENT.binomialTo2(sortedRobots.size() - i - 1 + mustFabric.size());
            long newTotalRevenue = revenueFarm + revenueFabric - climateCosts;
            if(newTotalRevenue > bestTotalRevenue) {
                bestI = i;
                bestTotalRevenue = newTotalRevenue;
            }
        }
        revenue = bestTotalRevenue;
        for(Robot r: mustFabric) {
            revenue += (TURNOVER_FABRIC - r.costM);
            System.out.println(r.costM);
            robotsFabric.add(r);
        }
        robotsFarm.addAll(sortedRobots.subList(0, bestI + 1));
        robotsFabric.addAll(sortedRobots.subList(bestI + 1, sortedRobots.size()));
        System.out.println(revenue);
        System.out.println(robotsFarm.size() + robotsFabric.size());
        return new Solution(robotsFabric, robotsFarm, revenue);
    }

    Solution linearMethod2(List<Robot> robots) {
        int numberRobots = robots.size();
        long revenue = 0;
        List<Robot> robotsFarm = new LinkedList<>();
        List<Robot> robotsFabric = new LinkedList<>();
        if(numberRobots <= 0) {
            return new Solution();
        }
        List<Robot> sortedRobots = new ArrayList<>(robots);
        //Alle Roboter, die eh mehr auf der Farm einbringen
        for(int i = 0; i < sortedRobots.size();) {
            Robot r = sortedRobots.get(i);
            int farmRevenue = TURNOVER_FARM - r.costF;
            if(farmRevenue >= TURNOVER_FABRIC - r.costM) {
                robotsFarm.add(r);
                revenue += farmRevenue;
                sortedRobots.remove(i);
            } else {
                i++;
            }

        }
        //Danach sortieren, wie viel sie mehr auf der Fabrik einbringen (aufsteigend sortiert)
        sortedRobots.sort(new Comparator<Robot>() {
            @Override
            public int compare(Robot r1, Robot r2) {
                int r1MoreRevenueFabric =  (TURNOVER_FABRIC - r1.costM) - (TURNOVER_FARM - r1.costF);
                int r2MoreRevenueFabric =  (TURNOVER_FABRIC - r2.costM) - (TURNOVER_FARM - r2.costF);
                return r1MoreRevenueFabric - r2MoreRevenueFabric;
            }
        });
        long revenueFarm = revenue;
        int revenueFabric = 0;
        long climateCosts = COEFFICIENT.binomialTo2(sortedRobots.size());
        for(Robot r: sortedRobots) {
            revenueFabric += TURNOVER_FABRIC - r.costM;
        }
        long bestTotalRevenue = revenueFarm + revenueFabric - climateCosts;
        int bestI = -1;
        for(int i = 0; i < sortedRobots.size(); i++) {
            Robot newFarmRobot = sortedRobots.get(i);
            revenueFarm += (TURNOVER_FARM - newFarmRobot.costF);
            revenueFabric -= (TURNOVER_FABRIC - newFarmRobot.costM);
            climateCosts = COEFFICIENT.binomialTo2(sortedRobots.size() - i - 1);
            long newTotalRevenue = revenueFarm + revenueFabric - climateCosts;
            if(newTotalRevenue > bestTotalRevenue) {
                bestI = i;
                bestTotalRevenue = newTotalRevenue;
            }
        }
        revenue = bestTotalRevenue;

        robotsFarm.addAll(sortedRobots.subList(0, bestI + 1));
        robotsFabric.addAll(sortedRobots.subList(bestI + 1, sortedRobots.size()));
        System.out.println(revenue);
        System.out.println(robotsFarm.size() + robotsFabric.size());
        return new Solution(robotsFabric, robotsFarm, revenue);
    }

    Solution knapsackDP(List<Robot> entities) {
        int n = entities.size(), W = entities.size();
        if (n <= 0) {
            return new Solution();
        }

        Solution[][] m = new Solution[n + 1][W + 1];
        for (int j = 0; j <= W; j++) {
            m[0][j] = new Solution();
        }

        for (int i = 0; i <= n; i++) {
            m[i][0] = new Solution();
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= W; j++) {
                final var last = entities.get(i - 1);

                final var revenueFarm = m[i - 1][j - 1].revenue + TURNOVER_FARM - last.costF;
                // We must subtract the previous bion. coeff. Otherwise, we substract it multiple times
                final var revenueFabric = m[i - 1][j].revenue + TURNOVER_FABRIC - last.costM - COEFFICIENT.binomialTo2(m[i - 1][j].fabric.size() + 1) + COEFFICIENT.binomialTo2(m[i - 1][j].fabric.size());

                if (revenueFarm > revenueFabric) {
                    final var farmCopy = m[i - 1][j - 1].copy();
                    farmCopy.farm.add(last);
                    farmCopy.revenue = revenueFarm;
                    m[i][j] = farmCopy;
                } else {
                    final var fabricCopy = m[i - 1][j].copy();
                    fabricCopy.fabric.add(last);
                    fabricCopy.revenue = revenueFabric;
                    m[i][j] = fabricCopy;
                }
            }
        }
        return m[n][W];
    }
}
