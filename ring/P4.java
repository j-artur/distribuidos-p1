package ring;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class P4 {
    public static void main(String[] args) throws Exception {
        List<String> hosts = Files.readString(Path.of("hosts.txt"))
            .lines()
            .collect(Collectors.toList());

        AddressAndPort host = new AddressAndPort(InetAddress.getByName(hosts.get(3)), 5004);
        AddressAndPort nextHost = new AddressAndPort(InetAddress.getByName(hosts.get(0)), 5001);

        Process p4 = new Process(4, host, nextHost);
        p4.run();
    }
}
