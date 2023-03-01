package ring;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    int id;
    int content;

    public Message(int id, int content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "[" + id + " - " + content + "]";
    }
}
