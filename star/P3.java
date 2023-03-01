package star;

public class P3 {
    public static void main(String[] args) {
        Process p3 = new Process(3, 5003, new SecondaryProcess());
        p3.run();
    }
}
