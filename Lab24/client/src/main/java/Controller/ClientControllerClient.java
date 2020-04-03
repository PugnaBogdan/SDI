package Controller;
import Entities.Client;
import Entities.Validators.Validator;
import Entities.Validators.ValidatorException;
import Message.Message;
import Message.MessageHeaders;
import tcp.TcpClient;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class ClientControllerClient implements ClientService {
    private ExecutorService executorService;
    private TcpClient tcpClient;


    public ClientControllerClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }


    public CompletableFuture<Void> addClient(Client client) throws ValidatorException, IOException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            System.out.println(client.toString());
            body.add(new AbstractMap.SimpleEntry<>("client",client));

            Message request = new Message(MessageHeaders.addClient,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
            throw new ValidatorException("client side error?");
        }
        },executorService);
    }

    public CompletableFuture<Void> deleteClient(int id) throws ValidatorException, IOException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            body.add(new AbstractMap.SimpleEntry<>("id",id));

            Message request = new Message(MessageHeaders.deleteClient,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("client side error?");
            }
        },executorService);
    }



    public CompletableFuture<Void> updateClient(Client client) throws ValidatorException, IOException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            System.out.println(client.toString());
            body.add(new AbstractMap.SimpleEntry<>("client",client));

            Message request = new Message(MessageHeaders.updateClient,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("client side error?");
            }
        },executorService);
    }

    public CompletableFuture<Set<Client>> getAllClients()
    {
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.getClients,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                    return(Set<Client>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("client side error?");
            }
        },executorService);
    }

    public CompletableFuture<Set<Client>> filterClientsId()
    {
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.filterClientsId,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(Set<Client>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("client side error?");
            }
        },executorService);
    }

    public CompletableFuture<Set<Client>> filterClientsName(int id)
    {
        return CompletableFuture.supplyAsync(()->
        {
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();
            body.add(new AbstractMap.SimpleEntry<>("id",id));

            Message request = new Message(MessageHeaders.filterClientsName,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(Set<Client>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("client side error?");
            }
        },executorService);
    }

}
