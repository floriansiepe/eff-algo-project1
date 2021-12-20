import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class InputReader {
    public List<Robot> readFile(String path) throws IOException {
        final var robots = new LinkedList<Robot>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String nextLine = br.readLine();
        while (nextLine != null) {
            try {
                String[] data = nextLine.split(",");
                Robot tmp = new Robot(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), data[4], data[5]);
                robots.add(tmp);
            } catch (NumberFormatException e) {

            }
            nextLine = br.readLine();
        }

        return robots;
    }
}
