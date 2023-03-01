package star;

import java.net.InetAddress;

public class AddressAndPort {
    InetAddress address;
    int port;

    public AddressAndPort(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public String toString() {
        return this.address + ":" + this.port;
    }
}