package src;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Solver {
  public static final int TURNOVER_FARM = 740;
  public static final int TURNOVER_FABRIC = 1800;
  public static final BinomialCoefficient COEFFICIENT = new BinomialCoefficient();
  int binom =0 ;

  public static void main(String[] args) throws IOException {
    final var inputReader = new InputReader();
    final var robots = inputReader.readFile("eff-algo-project1/Roboterliste.csv");
    final var solver = new Solver();
    final var solution = solver.solve(robots);
    final var solution2 = solver.solve2(robots);

    //System.out.println(robots);
    System.out.println(solution);
    System.out.println(solution2);
  }

  private Solution solve(final List<Robot> robots) {
    return knapsackDP(robots);
  }

  // all roboter: gewicht = 1, n = all roboter
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
        final var revenueFabric = m[i - 1][j].revenue + TURNOVER_FABRIC - last.costM - COEFFICIENT.biomialTo2(m[i - 1][j].fabric.size() + 1) + COEFFICIENT.biomialTo2(m[i - 1][j].fabric.size());

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

  private Solution solve2(List<Robot>robots){
    Collections.sort(robots);
    Solution dummy = new Solution();
    int delta=0;
    for (int i= robots.size()-1;i>=0;i--){
      delta = robots.size()-i;
      binom=((delta*(delta-1))/2)/delta;
      if( robots.get(i).oppCost-binom<0) {
        dummy.farm=robots.subList(0, i);
        break;
      }
      else {
        dummy.fabric.add(robots.get(i));
      }
    }

    return dummy;
  }
}