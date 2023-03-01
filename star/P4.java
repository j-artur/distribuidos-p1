package star;

public class P4 {
    public static void main(String[] args) {
        Process p4 = new Process(
            4,
            Process.getAddressMap().get(4).getAddress(),
            Process.getAddressMap().get(4).getPort(),
            new SecondaryProcess());
        p4.run();
    }
}
