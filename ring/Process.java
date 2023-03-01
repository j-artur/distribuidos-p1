package ring;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Process implements Runnable {
    private static Map<Integer, AddressAndPort> addressMap;

    public static Map<Integer, AddressAndPort> getAddressMap() {
        return addressMap;
    }

    private int id;
    private AddressAndPort host;

    Listener listener;
    Sender sender;

    static {
        try {
            List<String> hosts = Files.readString(Path.of("hosts.txt"))
                .lines()
                .collect(Collectors.toList());

            addressMap = Map.of(
                1, new AddressAndPort(InetAddress.getByName(hosts.get(0)), 5001),
                2, new AddressAndPort(InetAddress.getByName(hosts.get(1)), 5002),
                3, new AddressAndPort(InetAddress.getByName(hosts.get(2)), 5003),
                4, new AddressAndPort(InetAddress.getByName(hosts.get(3)), 5004)
            );
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    public Process(int id, AddressAndPort host, AddressAndPort nextHost) {
        this.id = id;
        this.host = host;

        listener = new Listener(id, host, nextHost);
        sender = new Sender(id, nextHost);
    }

    @Override
    public void run() {
        new Thread(listener).start();

        System.out.println("Process " + this.id + " is listening on " + this.host);

        sender.run();
    }
}
