package star;

import java.io.Serializable;

interface Destination extends Serializable {
}

class Broadcast implements Destination {
    @Override
    public String toString() {
        return "Broadcast";
    }
}

class Unicast implements Destination {
    int id;

    public Unicast(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Unicast(" + id + ")";
    }
}

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    private int origin;
    private Destination destination;
    private String content;

    public Message(int origin, Destination destination, String content) {
        this.origin = origin;
        this.destination = destination;
        this.content = content;
    }

    public int getOrigin() {
        return origin;
    }

    public Destination getDestination() {
        return destination;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message[from=" + origin + ", to=" + destination + ", content=" + content + "]";
    }
}
