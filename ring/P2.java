package ring;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class P2 {
    public static void main(String[] args) throws Exception {
        List<String> hosts = Files.readString(Path.of("hosts.txt"))
            .lines()
            .collect(Collectors.toList());

        AddressAndPort host = new AddressAndPort(InetAddress.getByName(hosts.get(1)), 5002);
        AddressAndPort nextHost = new AddressAndPort(InetAddress.getByName(hosts.get(2)), 5003);

        Process p2 = new Process(2, host, nextHost);
        p2.run();
    }
}
