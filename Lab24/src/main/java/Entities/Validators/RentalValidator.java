package Entities.Validators;

import Entities.Movie;
import Entities.RentAction;

public class RentalValidator implements Validator<RentAction> {

    @Override
    public void validate(RentAction rent) throws ValidatorException {
        if(rent.getRentId() < 0)
            throw new ValidatorException("Invalid ID!");

        if(rent.getMovieId() < 0)
            throw new ValidatorException("Invalid ID!");

        if(rent.getClientId()<0)
            throw new ValidatorException("Invalid ID!");
    }
}
