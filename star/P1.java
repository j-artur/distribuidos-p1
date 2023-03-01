package star;

public class P1 {
    public static void main(String[] args) throws Exception {
        Process p1 = new Process(
            1,
            Process.getAddressMap().get(1).getAddress(),
            Process.getAddressMap().get(1).getPort(),
            new PrimaryProcess());
        p1.run();
    }
}
