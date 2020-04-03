package Controller;

import Entities.Client;
import Message.Message;
import Message.MessageHeaders;
import org.xml.sax.SAXException;
import tcp.TcpServer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;

public class ServerController {
    TcpServer tcpServer;
    ClientService clientService;

    public ServerController(TcpServer srv, ClientService clientService)
    {
        tcpServer = srv;
        this.clientService = clientService;
    }

    public Message ok(String field, String msg)
    {
        Message response = new Message(MessageHeaders.good, new ArrayList<>());
        response.getBody().add(new AbstractMap.SimpleEntry<>(field, msg));
        return response;
    }

    public Message error(String field, String msg)
    {
        Message response = new Message(MessageHeaders.error, new ArrayList<>());
        response.getBody().add(new AbstractMap.SimpleEntry<>(field, msg));
        return response;
    }

    public void handlersToHeader() {

        this.tcpServer.addHandler(MessageHeaders.addClient,request->{
            try{
                return clientService.addClient((Client) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Client added")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });


        this.tcpServer.addHandler(MessageHeaders.updateClient,request->{
            try{
                return clientService.updateClient((Client) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Client updated")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });


        this.tcpServer.addHandler(MessageHeaders.deleteClient,request->{
            try{
                return clientService.deleteClient((int) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Client deleted")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });

        this.tcpServer.addHandler(MessageHeaders.getClients,
                request ->
                {
                    try {
                        return this.clientService.getAllClients()
                                .thenApply(
                                        clients -> {
                                            Message response = new Message(MessageHeaders.good, new ArrayList<>());
                                            response.getBody().add(new AbstractMap.SimpleEntry<>("clients", clients));
                                            return response;
                                        }
                                )
                                .exceptionally(
                                        e -> error("message", e.getMessage())
                                ).join();
                    } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                        e.printStackTrace();
                    }
                    return request;
                }
        );

        this.tcpServer.addHandler(MessageHeaders.filterClientsId,
                request ->
                {
                    try {
                        return this.clientService.filterClientsId()
                                .thenApply(
                                        clients -> {
                                            Message response = new Message(MessageHeaders.good, new ArrayList<>());
                                            response.getBody().add(new AbstractMap.SimpleEntry<>("clients", clients));
                                            return response;
                                        }
                                )
                                .exceptionally(
                                        e -> error("message", e.getMessage())
                                ).join();
                    } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                        e.printStackTrace();
                    }
                    return request;
                }
        );

        this.tcpServer.addHandler(MessageHeaders.filterClientsName,
                request ->
                {
                    try {
                        return this.clientService.filterClientsName((int) request.getBody().get(0).getValue())
                                .thenApply(
                                        clients -> {
                                            Message response = new Message(MessageHeaders.good, new ArrayList<>());
                                            response.getBody().add(new AbstractMap.SimpleEntry<>("clients", clients));
                                            return response;
                                        }
                                )
                                .exceptionally(
                                        e -> error("message", e.getMessage())
                                ).join();
                    } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                        e.printStackTrace();
                    }
                    return request;
                }
        );

    }





    public void runServer()
    {
        handlersToHeader();
        try
        {
            System.out.println("Server started.");
            tcpServer.startServer();
        } catch (Exception e)
        {

            System.out.println(e.getMessage());
        }
    }
}
