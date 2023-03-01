package star;

public class P4 {
    public static void main(String[] args) {
        Process p4 = new Process(4, 5004, new SecondaryProcess());
        p4.run();
    }
}
