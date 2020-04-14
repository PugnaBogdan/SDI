package Controller;

import Entities.RentAction;
import Entities.Validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface RentalService {
    Set<RentAction> getAllRentals() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    void addRental(RentAction rentalToAdd) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    void updateRental(RentAction rent)throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    void deleteRent(Integer rentToDelete)throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    List<Integer> getMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    List<Integer> getMostRentedMovie()throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
    List<String> getRentedMoviesOfMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException,SQLException;
}
