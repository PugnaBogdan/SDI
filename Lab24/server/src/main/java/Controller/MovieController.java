package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.Validators.MovieValidator;
import Entities.Validators.ValidatorException;
import Repository.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author Rares.
 */
public class MovieController implements MovieService {

    private Repository<Integer, Movie> repo;
    private MovieValidator validator;
    private ExecutorService executorService;

    public MovieController(Repository<Integer, Movie> initRepo, ExecutorService executorService) {
        repo = initRepo;
        validator = new MovieValidator();
        this.executorService = executorService;
    }

    public Optional<Movie> getById(Integer movieId) throws SQLException {
        return repo.findOne(movieId);
    }

    public CompletableFuture<Set<Movie>> getAllMovies() throws SQLException {
        Iterable<Movie> movies = repo.findAll();
        Set<Movie> set = new HashSet<>();
        return CompletableFuture.supplyAsync (()->{
            movies.forEach(set::add);
            return set;
        },executorService);

    }

    @Override

    public CompletableFuture<Void> addMovie(Movie movieToAdd) throws ValidatorException {
        return CompletableFuture.supplyAsync(()->{
            try
            {
                validator.validate(movieToAdd);
                repo.save(movieToAdd);
                return null;
            }
            catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
            {
                throw new ValidatorException(v.getMessage());
            }

        },executorService);
    }

    public CompletableFuture<Void> deleteMovie(int movieToDelete) throws ValidatorException{
        return CompletableFuture.supplyAsync(()-> {
            try {
                repo.delete(movieToDelete);
            } catch (ValidatorException v) {
                throw new ValidatorException((v.getMessage()));
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }
            return null;
        },executorService);
    }

    public CompletableFuture<Void> updateMovie(Movie updatedMovie) {
        return CompletableFuture.supplyAsync(()-> {
            try {
                repo.update(updatedMovie);

            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }
            return null;
        },executorService);
    }

    public CompletableFuture<Set<Movie>> filterEvenId() throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            Set<Movie> all = new HashSet<>();
            try {
                Iterable<Movie> movies = repo.findAll();
                movies.forEach(all::add);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            all.removeIf(movie -> movie.getId() % 2 == 0);

            return all;
        });
    }

    /*
    filters movies that have the title length less than some number
     */

    public CompletableFuture<Set<Movie>> filterMoviesWithTitleLessThan(int length) throws SQLException {
        return CompletableFuture.supplyAsync(()-> {
            Set<Movie> all = new HashSet<>();
            try {
                Iterable<Movie> movies = repo.findAll();
                movies.forEach(all::add);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            all.removeIf(movie -> movie.getTitle().length() > length);

            return all;
        });
    }
}
