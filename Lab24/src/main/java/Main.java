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
        Repository<Integer, Movie> movieRepository = new InMemoryRepository<>(movieValidator);
        Repository<Integer, Client> clientRepository = new InMemoryRepository<>(clientValidator);
        Repository<Integer, RentAction> rentActionRepository = new InMemoryRepository<>(rentalValidator);
        ClientController clientController = new ClientController(clientRepository);
        MovieController movieController = new MovieController(movieRepository);
        RentalController rentalController = new RentalController(rentActionRepository);
        UserInterface ui = new UserInterface(clientController, movieController, rentalController);
        ui.runConsole();

    }
}

