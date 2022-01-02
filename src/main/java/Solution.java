import java.util.LinkedList;
import java.util.List;

public class Solution {
    List<Robot> fabric = new LinkedList<>();
    List<Robot> farm = new LinkedList<>();
    long revenue = 0;

    public Solution() {
    }

    public Solution(final List<Robot> fabric, final List<Robot> farm, final long revenue) {
        this.fabric = fabric;
        this.farm = farm;
        this.revenue = revenue;
    }

    public Solution copy() {
        return new Solution(new LinkedList<>(fabric), new LinkedList<>(farm), revenue);
    }

    public long computeRevenue() {
        var revenue = (long) Solver.TURNOVER_FABRIC * fabric.size() - Solver.COEFFICIENT.binomialTo2(fabric.size()) + Solver.TURNOVER_FARM * farm.size();

        for (final Robot robot : fabric) {
            revenue-=robot.costM;
        }
        for (final Robot robot : farm) {
            revenue-=robot.costF;
        }
        return revenue;
    }

    @Override
    public String toString() {
        final var builder = new StringBuilder();
        builder.append("Solution\n");
        builder.append("Fabric:\n");
        for (final Robot robot : fabric) {
            builder.append(robot.toString());
            builder.append("\n");
        }

        builder.append("Farm:\n");
        for (final Robot robot : farm) {
            builder.append(robot.toString());
            builder.append("\n");
        }
        builder.append("Turnover by table:\n");
        builder.append(revenue);
        builder.append("\n");
        builder.append("Turnover directly by solution\n");
        builder.append(computeRevenue());
        return builder.toString();
    }
}
