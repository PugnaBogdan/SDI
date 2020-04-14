package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.Validators.ClientValidator;
import Entities.Validators.MovieValidator;
import Entities.Validators.ValidatorException;
import Repository.Repository;
import Repository.SpringDB.ClientSpringDBRepo;
import Repository.SpringDB.MovieSpringDBRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MovieSpringDBRepo movieRepo;

    @Autowired
    private MovieValidator movieValidator;

    public Optional<Movie> getById(Integer movieId) throws SQLException {
        return movieRepo.findOne(movieId);
    }

    public Set<Movie> getAllMovies() throws SQLException {
        Iterable<Movie> movies = movieRepo.findAll();
        Set<Movie> set = new HashSet<>();
            movies.forEach(set::add);
            return set;
    }

    public void addMovie(Movie movieToAdd) throws ValidatorException {
            try
            {
                movieValidator.validate(movieToAdd);
                movieRepo.save(movieToAdd);

            }
            catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
            {
                throw new ValidatorException(v.getMessage());
            }

    }

    public void deleteMovie(int movieToDelete) throws ValidatorException{
            try {
                movieRepo.delete(movieToDelete);
            } catch (ValidatorException v) {
                throw new ValidatorException((v.getMessage()));
            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }

    }

    public void updateMovie(Movie updatedMovie) {
            try {
                movieRepo.update(updatedMovie);

            } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
                e.printStackTrace();
            }
    }

    public Set<Movie> filterEvenId() throws SQLException {
            Set<Movie> all = new HashSet<>();
            try {
                Iterable<Movie> movies = movieRepo.findAll();
                movies.forEach(all::add);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            all.removeIf(movie -> movie.getId() % 2 == 0);

            return all;
    }

    /*
    filters movies that have the title length less than some number
     */

    public Set<Movie> filterMoviesWithTitleLessThan(int length) throws SQLException {
            Set<Movie> all = new HashSet<>();
            try {
                Iterable<Movie> movies = movieRepo.findAll();
                movies.forEach(all::add);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            all.removeIf(movie -> movie.getTitle().length() > length);

            return all;
    }
}
