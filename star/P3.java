package star;

public class P3 {
    public static void main(String[] args) {
        Process p3 = new Process(
            3,
            Process.getAddressMap().get(3).getAddress(),
            Process.getAddressMap().get(3).getPort(),
            new SecondaryProcess());
        p3.run();
    }
}
