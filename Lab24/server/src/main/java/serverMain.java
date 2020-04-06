import Controller.*;
import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ClientValidator;
import Entities.Validators.MovieValidator;
import Entities.Validators.RentalValidator;
import Entities.Validators.Validator;
import Repository.*;
import org.xml.sax.SAXException;
import tcp.TcpServer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pugna.
 */
public class serverMain {
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
        Validator<Movie> movieValidator = new MovieValidator();
        Validator<Client> clientValidator = new ClientValidator();
        Validator<RentAction> rentalValidator = new RentalValidator();
        InMemoryRepository movieRepository = new InMemoryRepository();


        Repository<Integer,RentAction> rentalDB = new RentalDBRepo();
        Repository<Integer,Client> clientDB = new ClientDBRepo();
        Repository<Integer, Movie> movieDB = new MovieDBRepo();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        ClientService clientController = new ClientController(clientDB,executorService);
        //MovieController movieController = new MovieController(movieDB);
        //RentalController rentalController = new RentalController(rentalDB, clientController, movieController);
        //UserInterface ui = new UserInterface(clientController, movieController, rentalController);
        //ui.runConsole();
        TcpServer server = new TcpServer(executorService);

        ServerController serverController = new ServerController(server,clientController);
        serverController.runServer();
    }
}
