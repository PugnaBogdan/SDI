package Controller;

        import Entities.Movie;
        import Entities.Validators.ValidatorException;
        import Repository.Repository;

        import java.util.Set;

/**
 * @author Rares.
 */
public class MovieController {

    private Repository<Integer, Movie> repo;

    public MovieController(Repository<Integer, Movie> initRepo) {
        repo = initRepo;
    }

    public Set<Movie> getAllMovies() {
        Iterable<Movie> movies = repo.findAll();
        return (Set<Movie>) movies;
    }

    public void addMovie(Movie movieToAdd) throws ValidatorException {
        try {
            repo.save(movieToAdd);
        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());
        }
    }
}
