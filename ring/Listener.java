package ring;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class Listener implements Runnable {
   private int id;
   private AddressAndPort host;
   private AddressAndPort nextHost;

   public Listener(int id, AddressAndPort host,  AddressAndPort nextHost) {
      this.id = id;
      this.host = host;
      this.nextHost = nextHost;
   }

   @Override
   public void run() {
      try (DatagramSocket socket = new DatagramSocket(host.getPort(), host.getAddress())) {
         byte[] receiveBuffer = new byte[1024];
         DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
         while (true) {
            socket.receive(packet);

            InputStream inputStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            List<Message> receivedList = (List<Message>) objectInputStream.readObject();

            if (receivedList.stream().filter(msg -> msg.id == this.id).count() > 0) {
               System.out.println("Received: " + receivedList);
               continue;
            }

            Message lastMessage = receivedList.get(receivedList.size() - 1);
            System.out.println("Received: " + lastMessage);

            int newContent = this.id % 2 == 0 ? lastMessage.content * 2 : lastMessage.content;
            Message newMsg = new Message(this.id, newContent);
            receivedList.add(newMsg);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(receivedList);
            objectOutputStream.flush();
            outputStream.flush();

            byte[] sendBuffer = outputStream.toByteArray();

            DatagramPacket sendPacket = new DatagramPacket(
               sendBuffer,
               sendBuffer.length,
               nextHost.getAddress(),
               nextHost.getPort());
            // send result to the client
            socket.send(sendPacket);
            System.out.println("Sent: " + newMsg.toString());
         }
      } catch (IOException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
