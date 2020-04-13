import Controller.*;
import Entities.Client;
import Entities.Movie;
import Entities.RentAction;
import Entities.Validators.ClientValidator;
import Entities.Validators.MovieValidator;
import Entities.Validators.RentalValidator;
import Entities.Validators.Validator;
import Repository.*;
import Repository.DB.ClientDBRepo;
import Repository.DB.MovieDBRepo;
import Repository.DB.RentalDBRepo;
import Repository.SpringDB.ClientSpringDBRepo;
import Repository.SpringDB.MovieSpringDBRepo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pugna.
 */
public class serverMain {
    public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, SQLException {

        System.out.println("server working");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "server.Config"
                );



    }
}

