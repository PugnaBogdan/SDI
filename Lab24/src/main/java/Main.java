import Controller.ClientController;
        import Controller.MovieController;
import Controller.RentalController;
import Entities.Client;
        import Entities.Movie;
        import Entities.Validators.ClientValidator;
        import Entities.Validators.MovieValidator;
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
        Repository<Integer, Movie> movieRepository = new InMemoryRepository<>(movieValidator);
        Repository<Integer, Client> clientRepository = new InMemoryRepository<>(clientValidator);
        ClientController clientController = new ClientController(clientRepository);
        MovieController movieController = new MovieController(movieRepository);
        UserInterface ui = new UserInterface(clientController, movieController);
        ui.runConsole();

    }
}

