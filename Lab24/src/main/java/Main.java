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
        import Repository.ClientFileRepository;
        import Repository.Repository;
        import Ui.UserInterface;

/**
 * @author Pugna.
 */
public class Main {
    public static void main(String args[])
    {
        Validator<Movie> movieValidator = new MovieValidator();
        Validator<Client> clientValidator = new ClientValidator();
        Validator<RentAction> rentalValidator = new RentalValidator();
        InMemoryRepository movieRepository = new InMemoryRepository();
        Repository<Integer, Client> clientRepository = new ClientFileRepository("D:\\anul2_sem2\\mpp\\SDI\\Lab24\\src\\main\\resources\\ClientRep.txt");
        InMemoryRepository rentActionRepository = new InMemoryRepository();
        ClientController clientController = new ClientController(clientRepository);
        MovieController movieController = new MovieController(movieRepository);
        RentalController rentalController = new RentalController(rentActionRepository, clientController, movieController);
        UserInterface ui = new UserInterface(clientController, movieController, rentalController);
        ui.runConsole();

    }
}

