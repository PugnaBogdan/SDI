package Ui;

import Controller.*;
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
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


public class UserInterface {
    private ExecutorService executorService;
    private ClientService clientService;
    private MovieService movieService;
    private RentalService rentalController;
    private static Scanner input = new Scanner(System.in);

    public UserInterface(ClientService clientController, MovieService movieService, RentalService rentalService, ExecutorService executorService) {
        this.clientService = clientController;
        this.movieService = movieService;
        this.rentalController = rentalService;
        this.executorService = executorService;
                //this.rentalController = rentalService;
    }


    public void addClient(String[] arguments)
    {
        CompletableFuture.supplyAsync(
                ()->{
                    Client newClient = new Client(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
                    try {
                        this.clientService.addClient(newClient);
                    } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                        e.printStackTrace();
                    }

                    return null;
                }).thenAcceptAsync(response -> System.out.println("Client added!"));
    }

    public void updateClient(String[] arguments)
    {
        try{
            Client newClient = new Client(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.clientService.updateClient(newClient);
            System.out.println("Client updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClient(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            clientService.deleteClient(id);
            System.out.println("Client deleted");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void printClients(String[] arguments) throws SAXException, InterruptedException, TransformerException, IOException, SQLException, ParserConfigurationException {
        CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return clientService.getAllClients();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }}).thenAcceptAsync(entity->entity.forEach(System.out::println));
    }


    public void filterClientsId(String[] arguments)
    {
        try{
            Set<Client> clients = clientService.filterClientsId();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void filterClientsName(String[] arguments)
    {

        int length = Integer.parseInt(arguments[3]);
        try{
            Set<Client> clients = clientService.filterClientsName(length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /// MOVIES ------------------------------------------------


    public void printMovies(String[] arguments) {
        CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return movieService.getAllMovies();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }}).thenAcceptAsync(entity->entity.forEach(System.out::println));

    }

    public void addMovie(String[] arguments)
    {
        try{
            Movie newMovie = new Movie(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.movieService.addMovie(newMovie);
            System.out.println("Movie added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMovie(String[] arguments)
    {
        try{
            Movie newMovie = new Movie(Integer.parseInt(arguments[2]),arguments[3],Integer.parseInt(arguments[4]));
            this.movieService.updateMovie(newMovie);
            System.out.println("Movie updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteMovie(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            movieService.deleteMovie(id);
            System.out.println("Movie deleted");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void filterMovieId(String[] arguments)
    {
        try{
            Set<Movie> movies = movieService.filterEvenId();
            movies.forEach(
                    System.out::println
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void filteMovieName(String[] arguments)
    {

        int length = Integer.parseInt(arguments[3]);
        try{
            Set<Movie> movies = movieService.filterMoviesWithTitleLessThan(length);
            movies.forEach(System.out::println);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    /// RENTALS -------------------------------------------------------------------------------

    public void printRents(String[] arguments) {
        CompletableFuture.supplyAsync(
                () -> {
                    try {
                        return rentalController.getAllRentals();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return null;
                    }}).thenAcceptAsync(entity->entity.forEach(System.out::println));

    }
    public void addRent(String[] arguments)
    {
        try{
            RentAction newRent = new RentAction(Integer.parseInt(arguments[2]),Integer.parseInt(arguments[3]),Integer.parseInt(arguments[4]));
            this.rentalController.addRental(newRent);
            System.out.println("Rent added!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateRent(String[] arguments)
    {
        try{
            RentAction newRent = new RentAction(Integer.parseInt(arguments[2]),Integer.parseInt(arguments[3]),Integer.parseInt(arguments[4]));
            this.rentalController.updateRental(newRent);
            System.out.println("Rental updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRent(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        try
        {
            int id = Integer.parseInt(arguments[2]);
            rentalController.deleteRent(id);
            System.out.println("Rent deleted");

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void mostActiveClient(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
            List<Integer> toDo = rentalController.getMostActiveClient();
            System.out.println(toDo.get(toDo.size()-1));
    }
    public void mostRentedMovies(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        List<Integer> toDo = rentalController.getMostRentedMovie();
        System.out.println(toDo.get(toDo.size()-1));
    }
    public void allRaports(String[] arguments) throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        List<String> toDo = rentalController.getRentedMoviesOfMostActiveClient();
        toDo.forEach(System.out::println);
    }
    private void printMenu(){
        System.out.println("Commands: ");
        System.out.println("add movie - add client - add rent");
        System.out.println("delete movie - delete client - delete rent");
        System.out.println("update movie - update client - update rent");
        System.out.println("filter client id 'or' name");
        System.out.println("filter movie  id 'or' title");
        System.out.println("raprot active - raport rented - raport all");
        System.out.println("exit = to get out of here");
    }
    public void run() throws SQLException, ParserConfigurationException, SAXException, IOException, TransformerException {
        String command;
        try {
            while (true) {
                //printMenu();
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
                        System.out.println("");
                    } else if (arguments[1].equals("movies")) {
                        printMovies(arguments);
                        System.out.println("");
                    } else if (arguments[1].equals("rents")) {
                        printRents(arguments);
                        System.out.println("");
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
                           filterMovieId(arguments);
                        }
                        else if(arguments[2].equals("title")){
                           filteMovieName(arguments);
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
                    else if(arguments[1].equals("all")){
                        allRaports(arguments);
                    }
                    else {
                        System.out.println("can't filter with that, sorry");
                    }
                }
                else if (arguments[0].equals("exit")){
                    break;
                }
                else {
                    System.out.println("can't do ¯\\_(ツ)_/¯");
                }
            }
        }
        catch (InterruptedIOException | InterruptedException e){
            e.printStackTrace();
        }
    }
}
