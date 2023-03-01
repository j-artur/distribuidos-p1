package star;

public class P2 {
    public static void main(String[] args) {
        Process p2 = new Process(
            2,
            Process.getAddressMap().get(2).getAddress(),
            Process.getAddressMap().get(2).getPort(),
            new SecondaryProcess());
        p2.run();
    }
}
