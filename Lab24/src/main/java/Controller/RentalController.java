package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.RentValidator;
import Entities.Validators.RentalException;
import Entities.Validators.ValidatorException;
import Repository.Repository;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RentalController {

    private Repository<Integer, RentAction> repo;
    private RentValidator validator;
    private ClientController clientController;
    private MovieController movieController;

    public RentalController(Repository<Integer, RentAction> repo, ClientController initClientController,MovieController initMovieController) {
        this.repo = repo;
        validator = new RentValidator();
        clientController = initClientController;
        movieController = initMovieController;
    }

    public Set<RentAction> getAllRentals() {
        Iterable<RentAction> rentals = repo.findAll();
        return (Set<RentAction>) rentals;
    }

    public void addRental(RentAction rentalToAdd) throws ValidatorException {
        try {
            validator.validate(rentalToAdd);

            int clientID = rentalToAdd.getClientId();
            int movieID = rentalToAdd.getMovieId();

            Optional<Client> c = clientController.getById(clientID);
            Optional<Movie> m = movieController.getById(movieID);

            if(c.get()==null)
                throw new ValidatorException("Client does not exist");

            if(m.get()==null)
                throw new ValidatorException("Movie does not exist");





            for(RentAction rent : repo.findAll())
                if(rent.getMovieId()==rentalToAdd.getMovieId())
                    throw new RentalException("Movie already rented!");

            repo.save(rentalToAdd);
        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());
        }
    }

    public void deleteRent(Integer rentToDelete) throws ValidatorException{
        try{
            repo.delete(rentToDelete);
        }
        catch (ValidatorException v){
            throw  new ValidatorException((v.getMessage()));
        }
    }

}
