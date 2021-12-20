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

    @Override
    public String toString() {
        return "Solution{" +
                "fabric=" + fabric +
                ", farm=" + farm +
                ", turnover=" + turnover +
                '}';
    }
}
