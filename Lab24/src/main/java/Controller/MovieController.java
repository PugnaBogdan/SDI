package Controller;

        import Entities.Movie;
        import Entities.Validators.MovieValidator;
        import Entities.Validators.ValidatorException;
        import Repository.Repository;

        import java.util.Optional;
        import java.util.Set;

/**
 * @author Rares.
 */
public class MovieController {

    private Repository<Integer, Movie> repo;
    private MovieValidator validator;

    public MovieController(Repository<Integer, Movie> initRepo) {
        repo = initRepo;
        validator = new MovieValidator();
    }

    public Optional<Movie> getById(Integer movieId)
    {
        return repo.findOne(movieId);
    }

    public Set<Movie> getAllMovies() {
        Iterable<Movie> movies = repo.findAll();
        return (Set<Movie>) movies;
    }

    public void addMovie(Movie movieToAdd) throws ValidatorException {
        try {
            validator.validate(movieToAdd);
            repo.save(movieToAdd);
        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());
        }
    }

    public void deleteMovie(Integer movieToDelete) throws ValidatorException{
        try{
            repo.delete(movieToDelete);
        }
        catch (ValidatorException v){
            throw  new ValidatorException((v.getMessage()));
        }
    }

    public Set<Movie> filterEvenId()
    {
        Set<Movie> all = (Set<Movie>) repo.findAll();
        all.removeIf(movie->movie.getId()%2==0);

        return all;
    }

    /*
    filters movies that have the title length less than some number
     */

    public Set<Movie> filterMoviesWithTitleLessThan(int length)
    {

        Set<Movie> all = (Set<Movie>) repo.findAll();
        all.removeIf(movie->movie.getTitle().length() < length);

        return all;
    }
}
