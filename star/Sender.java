package star;

import java.io.*;
import java.net.*;
import java.util.*;

public class Sender implements Runnable {
    private int id;
    ProcessType type;

    public Sender(int id, ProcessType type) {
        this.id = id;
        this.type = type;
    }

    public void send(Scanner scanner) {
        try (DatagramSocket socket = new DatagramSocket()) {
            System.out.println("Enter the message to send: ");
            String message = scanner.nextLine();
            System.out.println("Enter the receiver id (0 = Broadcast): ");
            int receiverId = scanner.nextInt();

            Destination destination = receiverId == 0 ? new Broadcast() : new Unicast(receiverId);

            Message msg = new Message(this.id, destination, message);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(msg);
            objectOutputStream.flush();
            outputStream.flush();

            byte[] writeBuffer = outputStream.toByteArray();

            DatagramPacket packet = new DatagramPacket(
                    writeBuffer,
                    writeBuffer.length,
                    Process.getAddressMap().get(1).getAddress(),
                    Process.getAddressMap().get(1).getPort());
            socket.send(packet);
            System.out.println("Sent: " + msg.getContent());
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
                send(scanner);

            if (input.equals("exit")) {
                scanner.close();
                System.exit(0);
            }
        }
    }
}