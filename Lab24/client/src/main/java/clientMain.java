
import Controller.ClientService;
import Ui.UserInterface;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class clientMain {
    public static void main(String args[]) throws SQLException, TransformerException, ParserConfigurationException, SAXException, IOException {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "clientConfig"
                );

        ClientService clientService = context.getBean(ClientService.class);
        //MovieControllerClient movieService = new MovieControllerClient();
        //RentalControllerClient rentalService = new RentalControllerClient();

        UserInterface console = new UserInterface(clientService);

        console.run();
    }
}
