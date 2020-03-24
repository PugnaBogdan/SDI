package Controller;

        import Entities.Client;
        import Entities.Movie;
        import Entities.RentAction;
        import Entities.Validators.MovieValidator;
        import Entities.Validators.ValidatorException;
        import Repository.Repository;
        import org.xml.sax.SAXException;

        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.transform.TransformerException;
        import java.io.IOException;
        import java.sql.SQLException;
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

    public Optional<Movie> getById(Integer movieId) throws SQLException {
        return repo.findOne(movieId);
    }

    public Set<Movie> getAllMovies() throws SQLException {
        Iterable<Movie> movies = repo.findAll();
        return (Set<Movie>) movies;
    }

    public void addMovie(Movie movieToAdd) throws ValidatorException {
        try {
            validator.validate(movieToAdd);
            repo.save(movieToAdd);
        } catch (ValidatorException v) {
            throw new ValidatorException(v.getMessage());
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMovie(Integer movieToDelete) throws ValidatorException{
        try{
            repo.delete(movieToDelete);
        }
        catch (ValidatorException v){
            throw  new ValidatorException((v.getMessage()));
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMovie(Movie updatedMovie) {
        try{
            Optional<Movie> m = getById(updatedMovie.getId());

            if(!m.isPresent())
                throw new ValidatorException("Movie does not exist");

            repo.update(updatedMovie);

        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Movie> filterEvenId() throws SQLException {
        Set<Movie> all = (Set<Movie>) repo.findAll();
        all.removeIf(movie->movie.getId()%2==0);

        return all;
    }

    /*
    filters movies that have the title length less than some number
     */

    public Set<Movie> filterMoviesWithTitleLessThan(int length) throws SQLException {

        Set<Movie> all = (Set<Movie>) repo.findAll();
        all.removeIf(movie->movie.getTitle().length() < length);

        return all;
    }
}
