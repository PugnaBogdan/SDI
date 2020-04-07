package Controller;

import Entities.RentAction;
import Entities.Validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface RentalService {
    CompletableFuture<Set<RentAction>> getAllRentals() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    CompletableFuture<Void> addRental(RentAction rentalToAdd) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    CompletableFuture<Void> updateRental(RentAction rent)throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    CompletableFuture<Void> deleteRent(Integer rentToDelete)throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    CompletableFuture<Void> deleteClientCascade(int clientToDelete)throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    CompletableFuture<Void> deleteMovieCascade(int movieToDelete)throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
}
