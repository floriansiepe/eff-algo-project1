import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution {
    List<Robot> fabric = new LinkedList<>();
    List<Robot> farm = new LinkedList<>();
    long turnover = 0;

    public Solution() {
    }

    public Solution(final List<Robot> fabric, final List<Robot> farm, final long turnover) {
        this.fabric = fabric;
        this.farm = farm;
        this.turnover = turnover;
    }

    public Solution copy() {
        return new Solution(new LinkedList<>(fabric), new LinkedList<>(farm), turnover);
    }

    public long computeRevenue() {
        var revenue = (long) Solver.TURNOVER_FABRIC * fabric.size() - Solver.COEFFICIENT.biomialTo2(fabric.size()) + Solver.TURNOVER_FARM * farm.size();

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
        builder.append("Turnover:\n");
        builder.append(turnover);
        builder.append("\n");
        builder.append(computeRevenue());
        return builder.toString();
    }
}
