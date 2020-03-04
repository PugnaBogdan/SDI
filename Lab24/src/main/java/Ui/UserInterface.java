package Ui;

        import Controller.ClientController;
        import Controller.MovieController;
        import Entities.Client;
        import Entities.Movie;
        import Entities.Validators.ValidatorException;

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

    public UserInterface(ClientController clientController1, MovieController movieController1) {
        this.clientController = clientController1;
        this.movieController = movieController1;
    }

    public void runConsole() {


        while (true){
            System.out.println("Commands: ");
            System.out.println(" 1 - add Client ");
            System.out.println(" 2 - show Clients");
            System.out.println(" 3 - add Movie ");
            System.out.println(" 4 - show Movies");
            System.out.println(" 5 - exit");
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
                    addMovie();
                }
                else if(command ==4){
                    printAllMovies();
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

    private void addClient() {

        Client client = readClient();
        try {
            clientController.addClient(client);
        } catch (ValidatorException e) {
            System.out.println(e.getMessage());
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


}
