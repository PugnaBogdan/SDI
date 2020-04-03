package tcp;

import Message.Message;
import Message.MessageHeaders;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TcpServer {
    private ExecutorService exeService;
    private Map<MessageHeaders, UnaryOperator<Message>> handlers;


    public TcpServer(ExecutorService exeService) {
        this.exeService = exeService;
        this.handlers = new HashMap<>();
    }

    public void addHandler(MessageHeaders methodName, UnaryOperator<Message> handler) {
        handlers.put(methodName, handler);
    }

    public void startServer() throws Exception {
        try (var serverSocket = new ServerSocket(Message.port)) {
            while (true) {
                Socket client = serverSocket.accept();
                exeService.submit(new ClientHandler(client));
            }
        }
        catch(Exception e)
        {
            throw new Exception("Client handling failed");
        }
    }


    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            try (var os = new ObjectOutputStream(socket.getOutputStream());
                 var is = new ObjectInputStream(socket.getInputStream())) {
                Message request = new Message();
                request.readFrom(is);

                System.out.println("received request" + request);
                System.out.println(request.getHeader());

                try {
                    Message response = handlers.get(request.getHeader()).apply(request);
                    System.out.println("request done");
                    response.writeTo(os);
                } catch (Exception e) {
                    ArrayList<Map.Entry<String, Object>> body = new ArrayList<>();
                    body.add(new AbstractMap.SimpleEntry<>("error", e.getMessage()));

                    System.out.println("request failed");
                    System.out.println(e.getMessage());

                    Message response = new Message(MessageHeaders.error, body);
                    response.writeTo(os);

                }

            } catch (Exception e) {
                try {
                    throw new Exception(e.getMessage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
