package Controller;

import Entities.Client;
import Entities.Movie;
import Entities.Validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class MovieControllerClient implements MovieService {


    @Override
    public CompletableFuture<Set<Movie>> getAllMovies() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> addMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> updateMovie(Movie movie) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteMovie(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Set<Movie>> filterEvenId() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Set<Movie>> filterMoviesWithTitleLessThan(int length) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public Optional<Movie> getById(Integer movieId) throws SQLException {
        return Optional.empty();
    }
}
