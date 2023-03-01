package ring;

import java.io.*;
import java.net.*;
import java.util.*;

public class Sender implements Runnable {
    private int id;
    private AddressAndPort nextHost;

    public Sender(int id, AddressAndPort nextHost) {
        this.id = id;
        this.nextHost = nextHost;
    }

    public void send() {
        try (DatagramSocket socket = new DatagramSocket()) {
            Message msg = new Message(this.id, this.id);
            List<Message> list = new ArrayList<>();
            list.add(msg);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(list);
            objectOutputStream.flush();
            outputStream.flush();

            byte[] writeBuffer = outputStream.toByteArray();

            DatagramPacket packet = new DatagramPacket(
                writeBuffer,
                writeBuffer.length,
                this.nextHost.getAddress(),
                this.nextHost.getPort());
            socket.send(packet);
            System.out.println("Sent: " + list);            
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("send"))
                send();

            if (input.equals("exit")) {
                scanner.close();
                System.exit(0);
            }
        }
    }
}
