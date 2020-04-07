package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ValidatorException;
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
    MovieService movieService;
    RentalService rentalService;

    public ServerController(TcpServer srv, ClientService clientService, MovieService movieService, RentalService rentalController)
    {
        tcpServer = srv;
        this.clientService = clientService;
        this.movieService = movieService;
        this.rentalService = rentalController;
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


        // CLIENT---------------------------------------------------------------------------


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


        // MOVIES ------------------------------------------------------------------


        this.tcpServer.addHandler(MessageHeaders.getMovies,
                request ->
                {
                    try {
                        return this.movieService.getAllMovies()
                                .thenApply(
                                        movies -> {
                                            Message response = new Message(MessageHeaders.good, new ArrayList<>());
                                            response.getBody().add(new AbstractMap.SimpleEntry<>("movies", movies));
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
        this.tcpServer.addHandler(MessageHeaders.updateMovie,request->{
            try{
                return movieService.updateMovie((Movie) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Movie updated")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });


        this.tcpServer.addHandler(MessageHeaders.deleteMovie,request->{
            try{
                return movieService.deleteMovie((int) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Movie deleted")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });
        this.tcpServer.addHandler(MessageHeaders.addMovie,request->{
            try{
                return movieService.addMovie((Movie) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Movie added")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });
        this.tcpServer.addHandler(MessageHeaders.filterEvenId,
                request ->
                {
                    try {
                        return this.movieService.filterEvenId()
                                .thenApply(
                                        movies -> {
                                            Message response = new Message(MessageHeaders.good, new ArrayList<>());
                                            response.getBody().add(new AbstractMap.SimpleEntry<>("movies", movies));
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
        this.tcpServer.addHandler(MessageHeaders.filterMoviesWithTitleLessThan,
                request ->
                {
                    try {
                        return this.movieService.filterMoviesWithTitleLessThan((int) request.getBody().get(0).getValue())
                                .thenApply(
                                        movies -> {
                                            Message response = new Message(MessageHeaders.good, new ArrayList<>());
                                            response.getBody().add(new AbstractMap.SimpleEntry<>("movies", movies));
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

        // RENTALS ---------------------------------------------------------------------------

        this.tcpServer.addHandler(MessageHeaders.getAllRentals,
                request ->
                {
                    try {
                        return this.rentalService.getAllRentals()
                                .thenApply(
                                        rentals -> {
                                            Message response = new Message(MessageHeaders.good, new ArrayList<>());
                                            response.getBody().add(new AbstractMap.SimpleEntry<>("rentals", rentals));
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
        this.tcpServer.addHandler(MessageHeaders.addRental,request->{
            try{
                return rentalService.addRental((RentAction) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Rent added")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });
        this.tcpServer.addHandler(MessageHeaders.updateRental,request->{
            try{
                return rentalService.updateRental((RentAction) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Rent updated")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });
        this.tcpServer.addHandler(MessageHeaders.deleteRent,request->{
            try{
                return rentalService.deleteRent((int) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","Rent deleted")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });
        this.tcpServer.addHandler(MessageHeaders.deleteMovieCascade,request->{
            try{
                return rentalService.deleteMovieCascade((int) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","movie deleted")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });
        this.tcpServer.addHandler(MessageHeaders.deleteClientCascade,request->{
            try{
                return rentalService.deleteClientCascade((int) request.getBody().get(0).getValue())
                        .thenApply(
                                client-> ok("message","client deleted")
                        ).exceptionally(
                                err -> error("error" ,err.getMessage())
                        ).join();
            }
            catch (Exception e)
            {
                return error("message",e.getMessage());
            }
        });


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
