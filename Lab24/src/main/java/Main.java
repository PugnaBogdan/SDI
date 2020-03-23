import Controller.ClientController;
        import Controller.MovieController;
import Controller.RentalController;
import Entities.Client;
        import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ClientValidator;
        import Entities.Validators.MovieValidator;
import Entities.Validators.RentalValidator;
import Entities.Validators.Validator;
        import Repository.InMemoryRepository;
        import Repository.RentalDBRepo;
        import Repository.ClientDBRepo;
        import Repository.ClientFileRepository;
        import Repository.ClientXMLRepository;
        import Repository.MovieXMLRepository;
        import Repository.RentalXMLRepository;
        import Repository.Repository;
        import Repository.MovieFileRepository;
        import Repository.RentalFileRepository;
        import Ui.UserInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author Pugna.
 */
public class Main {
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException {
        Validator<Movie> movieValidator = new MovieValidator();
        Validator<Client> clientValidator = new ClientValidator();
        Validator<RentAction> rentalValidator = new RentalValidator();
        InMemoryRepository movieRepository = new InMemoryRepository();


        //Repository<Integer, Client> clientRepository = new ClientXMLRepository("D:\anul2_sem2\mpp\SDI\Lab24\src\main\resources\ClientRep.txt");
        //Repository<Integer, Movie> movieFileRepository = new MovieFileRepository("D:\\anul2_sem2\\mpp\\SDI\\Lab24\\src\\main\\resources\\MovieRep.txt");
        //Repository<Integer, RentAction> rentalFileRepository = new RentalFileRepository("D:\\anul2_sem2\\mpp\\SDI\\Lab24\\src\\main\\resources\\RentalRep.txt");

        Repository<Integer, Client> clientRepository = new ClientXMLRepository("src/main/resources/ClientRep.xml");
        Repository<Integer, Movie> movieFileRepository = new MovieXMLRepository("src/main/resources/MovieRep.xml");
        //Repository<Integer, RentAction> rentalXMLRepository = new RentalXMLRepository("src/main/resources/RentRep.xml");

        Repository<Integer,RentAction> rentalDB = new RentalDBRepo();
        Repository<Integer,Client> clientDB = new ClientDBRepo();


        ClientController clientController = new ClientController(clientDB);
        MovieController movieController = new MovieController(movieFileRepository);
        RentalController rentalController = new RentalController(rentalDB, clientController, movieController);
        UserInterface ui = new UserInterface(clientController, movieController, rentalController);
        ui.runConsole();
    }
}

