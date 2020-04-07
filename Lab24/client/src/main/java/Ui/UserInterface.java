package Ui;

import Controller.ClientControllerClient;
import Controller.MovieControllerClient;
import Controller.RentalControllerClient;
import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;


public class UserInterface {
    private ClientControllerClient clientController;
    private MovieControllerClient movieController;
    private RentalControllerClient rentalController;
    private static Scanner input = new Scanner(System.in);

    public UserInterface(ClientControllerClient clientController, MovieControllerClient movieService, RentalControllerClient rentalService) {
        this.clientController = clientController;
        this.movieController = movieService;
        this.rentalController = rentalService;
    }


    public void addClient(String[] arguments)
    {
        try{
            Client newClient = new Client(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.clientController.addClient(newClient).thenAccept(
                    response -> System.out.println("Client added")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateClient(String[] arguments)
    {
        try{
            Client newClient = new Client(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.clientController.updateClient(newClient).thenAccept(
                    response -> System.out.println("Client update")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            clientController.deleteClient(id).thenAccept(
                    response -> System.out.println("Client deleted")
            ).exceptionally(
                    error->
                    {
                        System.out.println(error.getMessage());
                        return null;
                    }
            );
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void printClients(String[] arguments)
    {
        clientController.getAllClients().thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }

    public void filterClientsId(String[] arguments)
    {
        clientController.filterClientsId().thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }

    public void filterClientsName(String[] arguments)
    {

        int length = Integer.parseInt(arguments[3]);
        clientController.filterClientsName(length).thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }

    /// MOVIES ------------------------------------------------

    public void addMovie(String[] arguments)
    {
        try{
            Movie newMovie = new Movie(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.movieController.addMovie(newMovie).thenAccept(
                    response -> System.out.println("Movie added")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateMovie(String[] arguments)
    {
        try{
            Movie newMovie = new Movie(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.movieController.updateMovie(newMovie).thenAccept(
                    response -> System.out.println("Movie update")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMovie(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            movieController.deleteMovie(id).thenAccept(
                    response -> System.out.println("Movie deleted")
            ).exceptionally(
                    error->
                    {
                        System.out.println(error.getMessage());
                        return null;
                    }
            );
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void printMovies(String[] arguments)
    {
        movieController.getAllMovies().thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );

    }
    public void filterEvenId(String[] arguments)
    {
        movieController.filterEvenId().thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }
    public void filterMovieWithTitleLessThen(String[] arguments)
    {

        int length = Integer.parseInt(arguments[3]);
        movieController.filterMoviesWithTitleLessThan(length).thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );
    }

    /// RENTALS -------------------------------------------------------------------------------

    public void addRent(String[] arguments)
    {
        try{
            RentAction rentAction = new RentAction(Integer.parseInt(arguments[2]),Integer.parseInt(arguments[3]),Integer.parseInt(arguments[4]));
            this.rentalController.addRental(rentAction).thenAccept(
                    response -> System.out.println("rent added")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRent(String[] arguments)
    {
        try{
            RentAction newRentAction = new RentAction(Integer.parseInt(arguments[2]),Integer.parseInt(arguments[3]),Integer.parseInt(arguments[4]));
            this.rentalController.updateRental(newRentAction).thenAccept(
                    response -> System.out.println("rental updated")
            ).exceptionally(
                    er -> {
                        System.out.println(er.getMessage());
                        return null;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRent(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            rentalController.deleteRent(id).thenAccept(
                    response -> System.out.println("rent deleted")
            ).exceptionally(
                    error->
                    {
                        System.out.println(error.getMessage());
                        return null;
                    }
            );
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void deleteMovieCascade(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            rentalController.deleteMovieCascade(id).thenAccept(
                    response -> System.out.println("Movie deleted")
            ).exceptionally(
                    error->
                    {
                        System.out.println(error.getMessage());
                        return null;
                    }
            );
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void deleteClientCascade(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            rentalController.deleteClientCascade(id).thenAccept(
                    response -> System.out.println("Client deleted")
            ).exceptionally(
                    error->
                    {
                        System.out.println(error.getMessage());
                        return null;
                    }
            );
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void printRents(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        rentalController.getAllRentals().thenAccept(
                result-> {
                    result.forEach(System.out::println);
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );

    }
    public void mostActiveClient(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        rentalController.getMostActiveClient().thenAccept(
                result-> {
                    System.out.println(result.get(result.size()-1));
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );

    }
    public void mostRentedMovies(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        rentalController.getMostRentedMovie().thenAccept(
                result-> {
                    System.out.println(result.get(result.size()-1));
                }
        ).exceptionally(
                error->{
                    System.out.println(error.getMessage());
                    return null;
                }
        );

    }

    public void run() throws SQLException, ParserConfigurationException, SAXException, IOException, TransformerException {
        String command;
        try {
            while (true) {
                command = input.nextLine();
                String[] arguments = Arrays.stream(command.split(" ")).toArray(String[]::new);

                if (arguments[0].equals("add")) {
                    if (arguments[1].equals("client")) {
                        addClient(arguments);
                    }
                    else if(arguments[1].equals("movie")){
                        addMovie(arguments);
                    }
                    else if(arguments[1].equals("rent")){
                        addRent(arguments);
                    }
                    else {
                        System.out.println("idk who's that ¯\\_(ツ)_/¯");
                    }
                } else if (arguments[0].equals("print")) {
                    if (arguments[1].equals("clients")) {
                        printClients(arguments);
                    } else if (arguments[1].equals("movies")) {
                        printMovies(arguments);
                    } else if (arguments[1].equals("rents")) {
                        printRents(arguments);
                    }else {
                        System.out.println("idk who's that ¯\\_(ツ)_/¯");
                    }
                } else if (arguments[0].equals("update")) {
                    if (arguments[1].equals("client")) {
                        updateClient(arguments);
                    }
                    else if(arguments[1].equals("movie")){
                        updateMovie(arguments);
                    }
                    else if(arguments[1].equals("rent")){
                        updateRent(arguments);
                    }
                    else {
                        System.out.println("idk who's that ¯\\_(ツ)_/¯");
                    }
                } else if (arguments[0].equals("delete")) {
                    if (arguments[1].equals("client")) {
                        deleteClient(arguments);
                    }
                    else if(arguments[1].equals("movie")){
                        deleteMovie(arguments);
                    }
                    else if(arguments[1].equals("rent")){
                        deleteRent(arguments);
                    }
                    else {
                        System.out.println("idk who's that ¯\\_(ツ)_/¯");
                    }
                } else if (arguments[0].equals("filter")) {
                    if (arguments[1].equals("client")) {
                        if (arguments[2].equals("id")) {
                            filterClientsId(arguments);
                        } else if (arguments[2].equals("name")) {
                            filterClientsName(arguments);
                        } else {
                            System.out.println("can't filter with that, sorry");
                        }
                    }
                    else if(arguments[1].equals("movie")){
                        if(arguments[2].equals("id")){
                            filterEvenId(arguments);
                        }
                        else if(arguments[2].equals("title")){
                            filterMovieWithTitleLessThen(arguments);
                        }
                        else {
                            System.out.println("can't filter with that, sorry");
                        }
                    }
                    else {
                        System.out.println("idk who's that ¯\\_(ツ)_/¯");
                    }
                }
                else if (arguments[0].equals("raport")){
                    if (arguments[1].equals("active")){
                        mostActiveClient(arguments);
                    }
                    else if(arguments[1].equals("rented")){
                        mostRentedMovies(arguments);
                    }
                    else {
                        System.out.println("can't filter with that, sorry");
                    }
                }
                else {
                    System.out.println("can't do ¯\\_(ツ)_/¯");
                }
            }
        }
        catch (InterruptedIOException e){
            e.printStackTrace();
        }
    }
}
