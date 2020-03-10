package Controller;

        import Entities.Movie;
        import Entities.Validators.MovieValidator;
        import Entities.Validators.ValidatorException;
        import Repository.Repository;

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

    public void filterEvenId()
    {
        for (Movie movie: repo.findAll())
        {
            if(movie.getId()%2==0)
                repo.delete(movie.getId());
        }
    }

    /*
    filters movies that have the title length less than some number
     */

    public void filterMoviesWithTitleLessThan(int length)
    {
        for(Movie movie: repo.findAll())
        {
            if(movie.getTitle().length() < length)
                repo.delete(movie.getId());
        }
    }
}
