package Controller;

import Entities.Client;
import Entities.Movie;
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

public class MovieControllerClient implements MovieService {

    private ExecutorService executorService;
    private TcpClient tcpClient;


    public MovieControllerClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    public CompletableFuture<Set<Movie>> getAllMovies()
    {
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.getMovies,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(Set<Movie>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("Movie side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Void> addMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            System.out.println(movie.toString());
            body.add(new AbstractMap.SimpleEntry<>("movie",movie));

            Message request = new Message(MessageHeaders.addMovie,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("Movie side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Void> updateMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            System.out.println(movie.toString());
            body.add(new AbstractMap.SimpleEntry<>("movie",movie));

            Message request = new Message(MessageHeaders.updateMovie,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("movie side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Void> deleteMovie(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return CompletableFuture.supplyAsync(()->{
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();

            body.add(new AbstractMap.SimpleEntry<>("id",id));

            Message request = new Message(MessageHeaders.deleteMovie,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {
                return null;
            }
            else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }else {
                throw new ValidatorException("Movie side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Set<Movie>> filterEvenId(){
        return CompletableFuture.supplyAsync(()->
        {
            Message request = new Message(MessageHeaders.filterEvenId,null);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(Set<Movie>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("movie side error?");
            }
        },executorService);
    }

    @Override
    public CompletableFuture<Set<Movie>> filterMoviesWithTitleLessThan(int length) {
        return CompletableFuture.supplyAsync(()->
        {
            ArrayList<Map.Entry<String,Object>> body = new ArrayList<>();
            body.add(new AbstractMap.SimpleEntry<>("length",length));

            Message request = new Message(MessageHeaders.filterMoviesWithTitleLessThan,body);
            Message response = tcpClient.sendAndReceive(request);
            if(response.getHeader().equals(MessageHeaders.good))
            {

                return(Set<Movie>)(response.getBody().get(0).getValue());

            }else if(response.getHeader().equals(MessageHeaders.error))
            {
                throw new ValidatorException((String)response.getBody().get(0).getValue());
            }
            else {
                throw new ValidatorException("movie side error?");
            }
        },executorService);
    }

    @Override
    public Optional<Movie> getById(Integer movieId) throws SQLException {
        return Optional.empty();
    }

}
