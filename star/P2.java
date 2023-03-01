package star;

public class P2 {
    public static void main(String[] args) {
        Process p2 = new Process(2, 5002, new SecondaryProcess());
        p2.run();
    }
}
