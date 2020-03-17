package Ui;

        import Controller.ClientController;
        import Controller.MovieController;
        import Controller.RentalController;
        import Entities.Client;
        import Entities.Movie;
        import Entities.RentAction;
        import Entities.Validators.ValidatorException;
        import org.xml.sax.SAXException;

        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.transform.TransformerException;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.Set;

/**
 * @author Pugna.
 */
public class UserInterface {

    private ClientController clientController;
    private MovieController movieController;
    private RentalController rentalController;

    public UserInterface(ClientController clientController1, MovieController movieController1, RentalController rentalController) {
        this.clientController = clientController1;
        this.movieController = movieController1;
        this.rentalController = rentalController;
    }

    public void runConsole() {


        while (true){
            System.out.println("Commands: ");
            System.out.println(" 1 - add Client ");
            System.out.println(" 2 - show Clients");
            System.out.println(" 3 - delete Client ");
            System.out.println(" 4 - add Movie ");
            System.out.println(" 5 - show Movies");
            System.out.println(" 6 - delete Movie");
            System.out.println(" 7 - add Rent");
            System.out.println(" 8 - show Rents");
            System.out.println(" 9 - delete Rent");
            System.out.println(" 10 - filter client by odd Id");
            System.out.println(" 11 - filter movie by title length");
            System.out.println(" 12 - filter movie by even ID");
            System.out.println(" 13 - filter client by name length");
            System.out.println(" 0 - exit");
            System.out.println("Input command: ");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

            try {
                int command = Integer.parseInt(bufferRead.readLine());
                if(command == 1){
                    addClient();
                }
                else if(command ==2){
                    printAllClients();
                }
                else if(command ==3){
                    deleteClient();
                }
                else if(command ==4){
                    addMovie();
                }
                else if(command ==5){
                    printAllMovies();
                }
                else if(command ==6){
                    deleteMovie();
                }
                else if(command ==7){
                    addRent();
                }
                else if(command ==8){
                    printAllRents();
                }
                else if(command ==9){
                    deleteRent();
                }
                else if(command ==10){
                    filterOddIdClient();
                }
                else if(command ==11){
                    filterMovieByNameLength();
                }
                else if(command ==12){
                    filterEvenMovie();
                }
                else if(command ==13){
                    filterClientByNameLength();
                }
                else break;

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void printAllClients() {
        Set<Client> clients = clientController.getAllClients();
        clients.forEach(System.out::println);
    }

    private void printAllMovies() {
        Set<Movie> students = movieController.getAllMovies();
        students.forEach(System.out::println);
    }
    private void printAllRents() {
        Set<RentAction> rents = rentalController.getAllRentals();
        rents.forEach(System.out::println);
    }


    private void addClient() {

        Client client = readClient();
        try {
            clientController.addClient(client);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

    }
    private void addMovie() {

        Movie movie = readMovie();
        try {
            movieController.addMovie(movie);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

    }
    private void addRent() {

        RentAction rent = readRent();
        try {
            rentalController.addRental(rent);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }

    }

    private Client readClient() {
        System.out.println("Read client {id, name, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            String name = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());// ...
            Client client = new Client(id, name, age);


            return client;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Movie readMovie() {
        System.out.println("Read movie {id, title, price}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            String titel = bufferRead.readLine();
            int price = Integer.parseInt(bufferRead.readLine());// ...
            Movie movie = new Movie(id, titel, price);


            return movie;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private RentAction readRent() {
        System.out.println("Read rental {rentId, clientId, movieId}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            int id1 = Integer.parseInt(bufferRead.readLine());// ...
            int id2 = Integer.parseInt(bufferRead.readLine());// ...
            RentAction rent = new RentAction(id, id1, id2);


            return rent;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * deletes a client
     */
    private void deleteClient(){
        System.out.println("Type client id you want to delete: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            int id = Integer.parseInt(bufferRead.readLine());
            clientController.deleteClient(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteMovie(){
        System.out.println("Type movie id you want to delete: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            int id = Integer.parseInt(bufferRead.readLine());
            movieController.deleteMovie(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteRent(){
        System.out.println("Type rent id you want to delete: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            int id = Integer.parseInt(bufferRead.readLine());
            rentalController.deleteRent(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterOddIdClient()
    {
        Set<Client> filtered = clientController.filterOddId();

        filtered.forEach(System.out::println);
    }
    private void filterEvenMovie()
    {
        Set<Movie> filtered = movieController.filterEvenId();
        filtered.forEach(System.out::println);
    }

    private void filterMovieByNameLength() throws IOException {
        System.out.println("Type title length: ");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Set<Movie> filtered = movieController.filterMoviesWithTitleLessThan(Integer.parseInt(bufferRead.readLine()));
        filtered.forEach(System.out::println);
    }
    private void filterClientByNameLength() throws IOException {
        System.out.println("Type name length: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Set<Client> filtered = clientController.filterClientsWithNameLessThan(Integer.parseInt(bufferRead.readLine()));
        filtered.forEach(System.out::println);
    }


}
