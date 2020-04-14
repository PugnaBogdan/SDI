package ServerController;

import Controller.ClientController;
import Controller.MovieController;
import Controller.MovieService;
import Entities.Movie;
import Entities.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ServerControllerMovie implements MovieService {
    @Autowired
    private MovieService movieController;

    @Override
    public Set<Movie> getAllMovies() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return movieController.getAllMovies();
    }

    @Override
    public void addMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        movieController.addMovie(movie);
    }

    @Override
    public void updateMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        movieController.updateMovie(movie);
    }

    @Override
    public void deleteMovie(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        movieController.deleteMovie(id);
    }

    @Override
    public Set<Movie> filterEvenId() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return movieController.filterEvenId();
    }

    @Override
    public Set<Movie> filterMoviesWithTitleLessThan(int length) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return movieController.filterMoviesWithTitleLessThan(length);
    }

    @Override
    public Optional<Movie> getById(Integer movieId) throws SQLException {
        return movieController.getById(movieId);
    }
}
