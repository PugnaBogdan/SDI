package Message;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Message implements Serializable {

    public static final int port = 1234;
    public static final String host = "localhost";

    private MessageHeaders header;
    private List<Map.Entry<String,Object>> body;

    public Message(MessageHeaders head,List<Map.Entry<String,Object>>body)
    {
        this.header = head;
        this.body = body;
    }

    public Message() {

    }

    public MessageHeaders getHeader() {
        return header;
    }

    public void setHeader(MessageHeaders header) {
        this.header = header;
    }

    public List<Map.Entry<String, Object>> getBody() {
        return body;
    }

    public void setBody(List<Map.Entry<String, Object>> body) {
        this.body = body;
    }

    public void writeTo(ObjectOutputStream os) throws Exception
    {
        try
        {
            os.writeObject(this);
        }
        catch(IOException e)
        {

            //e.printStackTrace();
            throw new Exception("Can't write object");
        }
    }

    public void readFrom(ObjectInputStream is) throws Exception {

        try
        {
            Message m = (Message) is.readObject();

            this.setHeader(m.getHeader());
            this.setBody(m.getBody());

        }
        catch(ClassNotFoundException e)
        {
            throw new Exception("Can't write msg");
        }


    }

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
