package Controller;

import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class RentalControllerClient implements RentalService {
    @Autowired
    RentalService rentalController;

    @Override
    public Set<RentAction> getAllRentals() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return rentalController.getAllRentals();
    }

    @Override
    public void addRental(RentAction rentalToAdd) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        rentalController.addRental(rentalToAdd);
    }

    @Override
    public void updateRental(RentAction rent) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        rentalController.updateRental(rent);
    }

    @Override
    public void deleteRent(Integer rentToDelete) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        rentalController.deleteRent(rentToDelete);
    }

    @Override
    public List<Integer> getMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return rentalController.getMostActiveClient();
    }

    @Override
    public List<Integer> getMostRentedMovie() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return rentalController.getMostRentedMovie();
    }

    @Override
    public List<String> getRentedMoviesOfMostActiveClient() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return rentalController.getRentedMoviesOfMostActiveClient();
    }
}
