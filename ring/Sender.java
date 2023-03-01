package ring;

import java.io.*;
import java.net.*;
import java.util.*;

public class Sender implements Runnable {
    private int id;
    private InetAddress host;
    private int port;

    public Sender(int id, InetAddress host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
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

            DatagramPacket packet = new DatagramPacket(writeBuffer, writeBuffer.length, this.host, this.port);
            socket.send(packet);
            System.out.println("Sent: " + list);

            byte[] readBuffer = new byte[1024];

            DatagramPacket receiverPacket = new DatagramPacket(readBuffer, readBuffer.length);

            // retrieve result
            socket.receive(receiverPacket);

            InputStream inputStream = new ByteArrayInputStream(readBuffer);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            List<Message> receivedList = (List<Message>) objectInputStream.readObject();

            System.out.println("Received: " + receivedList);
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
