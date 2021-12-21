import java.io.IOException;
import java.util.List;

public class Solver {
    public static final int TURNOVER_FARM = 740;
    public static final int TURNOVER_FABRIC = 1800;
    public static final BinomialCoefficient COEFFICIENT = new BinomialCoefficient();

    public static void main(String[] args) throws IOException {
        final var inputReader = new InputReader();
        final var robots = inputReader.readFile("Roboterliste.csv");
        final var solver = new Solver();
        final var solution = solver.solve(robots);
        System.out.println(solution);
    }

    private Solution solve(final List<Robot> robots) {
        return knapsackDP(robots);
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

                final var farmCopy = m[i - 1][j - 1].copy();
                farmCopy.farm.add(last);
                final var revenueFarm = farmCopy.computeRevenue();

                final var fabricCopy = m[i - 1][j].copy();
                fabricCopy.fabric.add(last);
                final var revenueFabric = fabricCopy.computeRevenue();


                if (revenueFarm > revenueFabric) {
                    farmCopy.turnover = revenueFarm;
                    m[i][j] = farmCopy;
                } else {
                    fabricCopy.turnover = revenueFabric;
                    m[i][j] = fabricCopy;
                }
            }
        }
        return m[n][W];
    }
}
