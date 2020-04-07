package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.Validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface MovieService {
    CompletableFuture<Set<Movie>> getAllMovies() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Void> addMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Void> updateMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Void> deleteMovie(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Set<Movie>> filterEvenId() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Set<Movie>> filterMoviesWithTitleLessThan(int length) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    Optional<Movie> getById(Integer movieId) throws SQLException;
}
