package Entities.Validators;

        import Entities.Movie;

/**
 * @author Rares.
 */

public class MovieValidator implements Validator<Movie> {

    @Override
    public void validate(Movie movie) throws ValidatorException {
        if(movie.getId() < 0)
            throw new ValidatorException("Invalid ID!");

        if(movie.getTitle().equals(""))
            throw new ValidatorException("Empty Title!");

        if(movie.getPrice()<0)
            throw new ValidatorException("Negative price!");
    }
}
