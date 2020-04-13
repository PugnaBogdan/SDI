package Controller;

import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
public class RentalControllerClient implements RentalService {

    @Override
    public CompletableFuture<Set<RentAction>> getAllRentals() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> addRental(RentAction rentalToAdd) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> updateRental(RentAction rent) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteRent(Integer rentToDelete) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteClientCascade(int clientToDelete) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteMovieCascade(int movieToDelete) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<List<Integer>> getMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<List<Integer>> getMostRentedMovie() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }

    @Override
    public CompletableFuture<List<String>> getRentedMoviesOfMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return null;
    }
}
