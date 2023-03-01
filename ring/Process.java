package ring;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Process implements Runnable {
    private static InetAddress host = null;
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

    public Process(int id, int port, int nextPort) {
        this.id = id;
        this.port = port;

        listener = new Listener(id, host, port, nextPort);
        sender = new Sender(id, host, nextPort);
    }

    @Override
    public void run() {
        new Thread(listener).start();

        System.out.println("Process " + this.id + " is listening on port " + this.port);

        sender.run();
    }
}
