package Controller;

import Entities.RentAction;
import Entities.Validators.RentalException;
import Entities.Validators.ValidatorException;
import Repository.Repository;

import java.util.Iterator;
import java.util.Set;

public class RentalController {

    private Repository<Integer, RentAction> repo;

    public RentalController(Repository<Integer, RentAction> repo) {
        this.repo = repo;
    }

    public Set<RentAction> getAllRentals() {
        Iterable<RentAction> rentals = repo.findAll();
        return (Set<RentAction>) rentals;
    }

    public void addRental(RentAction rentalToAdd) throws ValidatorException {
        try {

            for(RentAction rent : repo.findAll())
                if(rent.getMovieId()==rentalToAdd.getMovieId())
                    throw new RentalException("Movie already rented!");

            repo.save(rentalToAdd);
        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());
        }
    }
}
