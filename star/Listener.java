package star;

import java.io.*;
import java.net.*;

public class Listener implements Runnable {
    private int id;
    private InetAddress host;
    private int port;
    private ProcessType type;

    public Listener(int id, InetAddress host, int port, ProcessType type) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.type = type;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket(this.port, this.host)) {
            byte[] receiveBuffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            while (true) {
                socket.receive(packet);                

                InputStream inputStream = new ByteArrayInputStream(packet.getData());
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                Message received = (Message) objectInputStream.readObject();

                if (this.type instanceof SecondaryProcess) {
                    System.out.println("Received from " + received.getOrigin() + ": " + received.getContent());
                    continue;
                }

                if (received.getDestination() instanceof Unicast) {
                    Unicast unicast = (Unicast) received.getDestination();

                    int destination = unicast.id;

                    if (destination == this.id) {
                        System.out.println("Received from " + received.getOrigin() + ": " + received.getContent());
                    } else {
                        AddressAndPort nextAddress = Process.getAddressMap().get(destination);

                        if (nextAddress == null) {
                            System.out.println("No such destination: " + destination);
                            continue;
                        }

                        DatagramPacket newPacket = new DatagramPacket(
                                receiveBuffer,
                                receiveBuffer.length,
                                nextAddress.getAddress(),
                                nextAddress.getPort());
                        socket.send(newPacket);
                        System.out.println("Re-sent from " + received.getOrigin() + " to " + destination + ": "
                                + received.getContent());
                    }
                } else {
                    System.out.println("Broadcast from " + received.getOrigin() + ": " + received.getContent());
                    for (var entry : Process.getAddressMap().entrySet()) {
                        if (entry.getKey().equals(1))
                            continue;

                        DatagramPacket newPacket = new DatagramPacket(
                                receiveBuffer,
                                receiveBuffer.length,
                                entry.getValue().getAddress(),
                                entry.getValue().getPort());
                        socket.send(newPacket);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
