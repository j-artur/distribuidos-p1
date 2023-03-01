package star;

import java.util.Map;

public class P1 {
    public static void main(String[] args) {
        Process p1 = new Process(1, Process.PRIMARY_PORT, new PrimaryProcess(Map.of(2, 5002, 3, 5003, 4, 5004)));
        p1.run();
    }
}
