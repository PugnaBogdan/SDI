package Ui;

        import Controller.ClientController;
        import Controller.MovieController;
        import Controller.RentalController;
        import Entities.Client;
        import Entities.Movie;
        import Entities.RentAction;
        import Entities.Validators.RentalException;
        import Entities.Validators.ValidatorException;
        import org.xml.sax.SAXException;

        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.transform.TransformerException;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.Collection;
        import java.util.List;
        import java.util.Map;

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
            System.out.println(" 1 - add Client |  2 - show Clients | 3 - Update Client | 4 - delete Client");
            System.out.println(" 5 - add Movie | 6 - show Movies | 7 - Update Movie | 8 - delete Movie");
            System.out.println(" 9 - add Rent | 10 - show Rents | 11 - Update Rents | 12 - delete Rents");
            System.out.println(" 13 - filter client by odd Id");
            System.out.println(" 14 - filter movie by title length");
            System.out.println(" 15 - filter movie by even ID");
            System.out.println(" 16 - filter client by name length");
            System.out.println(" 17 - reports");
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
                    updateClient();
                }
                else if(command ==4){
                    deleteClient();
                }
                else if(command ==5){
                    addMovie();
                }
                else if(command ==6){
                    printAllMovies();
                }
                else if(command ==7){
                    updateMovie();
                }
                else if(command ==8){
                    deleteMovie();
                }
                else if(command == 9){
                    addRent();
                }
                else if(command ==10){
                    printAllRents();
                }
                else if(command ==11){
                    updateRental();
                }
                else if(command ==12){
                    deleteRent();
                }
                else if(command ==13){
                    filterOddIdClient();
                }
                else if(command ==14){
                    filterMovieByNameLength();
                }
                else if(command ==15){
                    filterEvenMovie();
                }
                else if(command ==16){
                    filterClientByNameLength();
                }
                else if(command ==17)
                {
                    getReports();
                }
                else break;

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    private void getReports() {

        rentalController.updateTheReports();

        List<Integer> mostActive = (List<Integer>) rentalController.getMostActiveClient();
        System.out.println("The most active client is:" + Integer.toString((Integer) mostActive.get(0)));

        List<Integer> mostRented = (List<Integer>) rentalController.getMostRentedMovie();
        System.out.println("The most rented movie is:" + Integer.toString((Integer) mostRented.get(0)));

        Map<Integer,Integer> repeatedRentals =  rentalController.getRepeatedRentals();
        System.out.println("The repeated rentals are:");
        System.out.println(repeatedRentals);

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
        } catch (RentalException e) {
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
            rentalController.deleteClient(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void deleteMovie(){
        System.out.println("Type movie id you want to delete: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            int id = Integer.parseInt(bufferRead.readLine());
            rentalController.deleteMovie(id);
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

    private Client updateClient(){
        System.out.println("Read new clients attributes {id, name, age}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            String name = bufferRead.readLine();
            int age = Integer.parseInt(bufferRead.readLine());// ...

            Client newCLient = new Client(id, name, age);

            clientController.updateClient(newCLient);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }
    private Movie updateMovie(){
        System.out.println("Read new movie attributes {id, ClientId, MovieId}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            String titel = bufferRead.readLine();
            int price = Integer.parseInt(bufferRead.readLine());// ...
            Movie movie = new Movie(id, titel, price);

            movieController.updateMovie(movie);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;

    }
    private Client updateRental(){
        System.out.println("Read new rental {rentId, clientId, movieId}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int id = Integer.parseInt(bufferRead.readLine());// ...
            int id1 = Integer.parseInt(bufferRead.readLine());// ...
            int id2 = Integer.parseInt(bufferRead.readLine());// ...
            RentAction rent = new RentAction(id, id1, id2);

            rentalController.updateRental(rent);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
        }
        return null;

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
