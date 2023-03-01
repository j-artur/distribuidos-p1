package star;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

interface ProcessType {
}

class PrimaryProcess implements ProcessType {
    private Map<Integer, Integer> ports;

    public PrimaryProcess(Map<Integer, Integer> ports) {
        this.ports = ports;
    }

    public Map<Integer, Integer> getPorts() {
        return ports;
    }
}

class SecondaryProcess implements ProcessType {
}

public class Process implements Runnable {
    private static InetAddress host = null;
    public static final int PRIMARY_PORT = 5001;
    private int id;
    private int port;

    Listener listener;
    Sender sender;

    {
        try {
            String hostName = Files.readString(Path.of("host.txt"));

            host = InetAddress.getByName(hostName);
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    public Process(int id, int port, ProcessType type) {
        this.id = id;
        this.port = port;

        listener = new Listener(id, host, port, type);
        sender = new Sender(id, host, port, type);
    }

    @Override
    public void run() {
        new Thread(listener).start();

        System.out.println("Process " + this.id + " is listening on port " + this.port);

        sender.run();
    }
}
