package Entities.Validators;

import Entities.RentAction;

public class RentValidator implements Validator<RentAction> {

    @Override
    public void validate(RentAction rent) throws ValidatorException {
        if(rent.getId() < 0)
            throw new ValidatorException("Invalid Rent Id!");

        if(rent.getMovieId()<0)
            throw new ValidatorException("Invalid Client Id!");

        if(rent.getClientId()<0)
            throw new ValidatorException("Invalid Movie Id!");
    }
}