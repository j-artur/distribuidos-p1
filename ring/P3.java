package ring;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class P3 {
    public static void main(String[] args) throws Exception {
        List<String> hosts = Files.readString(Path.of("hosts.txt"))
            .lines()
            .collect(Collectors.toList());

        AddressAndPort host = new AddressAndPort(InetAddress.getByName(hosts.get(2)), 5003);
        AddressAndPort nextHost = new AddressAndPort(InetAddress.getByName(hosts.get(3)), 5004);

        Process p3 = new Process(3, host, nextHost);
        p3.run();
    }
}
