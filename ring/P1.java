package ring;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class P1 {
    public static void main(String[] args) throws Exception {
        List<String> hosts = Files.readString(Path.of("hosts.txt"))
            .lines()
            .collect(Collectors.toList());

        AddressAndPort host = new AddressAndPort(InetAddress.getByName(hosts.get(0)), 5001);
        AddressAndPort nextHost = new AddressAndPort(InetAddress.getByName(hosts.get(1)), 5002);

        Process p1 = new Process(1, host, nextHost);
        p1.run();
    }
}
