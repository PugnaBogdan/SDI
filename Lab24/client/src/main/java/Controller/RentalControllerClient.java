package Controller;

import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ValidatorException;
import Message.Message;
import Message.MessageHeaders;
import org.xml.sax.SAXException;
import tcp.TcpClient;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class RentalControllerClient implements RentalService {


    private ExecutorService executorService;
    private TcpClient tcpClient;

    public RentalControllerClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<Set<RentAction>> getAllRentals() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.getAllRentals,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(Set<RentAction>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("Rental side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Void> addRental(RentAction rentalToAdd) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            System.out.println(rentalToAdd.toString());
            body.add(new AbstractMap.SimpleEntry<>("rent",rentalToAdd));

            Message request = new Message(MessageHeaders.addRental,body);
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

    @Override
    public CompletableFuture<Void> updateRental(RentAction rent) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            System.out.println(rent.toString());
            body.add(new AbstractMap.SimpleEntry<>("rent",rent));

            Message request = new Message(MessageHeaders.updateRental,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("rental side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Void> deleteRent(Integer rentToDelete) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            body.add(new AbstractMap.SimpleEntry<>("id",rentToDelete));

            Message request = new Message(MessageHeaders.deleteRent,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("rental side error?");
            }
        },executorService);
    }
    @Override
    public CompletableFuture<List<Integer>> getMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.getMostActiveClient,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(List<Integer>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("Rental side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<List<Integer>> getMostRentedMovie() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.getMostRentedMovie,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(List<Integer>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("Rental side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<List<String>> getRentedMoviesOfMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.getRentedMoviesOfMostActiveClient,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(List<String>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("Rental side error?");
            }
        },executorService);
    }


    /// DOESN'T MATTER ANYMORE-----------------------
    @Override
    public CompletableFuture<Void> deleteClientCascade(int clientToDelete) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            body.add(new AbstractMap.SimpleEntry<>("id",clientToDelete));

            Message request = new Message(MessageHeaders.deleteClientCascade,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("rental side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Void> deleteMovieCascade(int movieToDelete) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            body.add(new AbstractMap.SimpleEntry<>("id",movieToDelete));

            Message request = new Message(MessageHeaders.deleteMovieCascade,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("rental side error?");
            }
        },executorService);
    }


}
