
import Controller.ClientService;
import Controller.MovieService;
import Controller.RentalService;
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

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        System.out.println("client on");
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "clientConfig"
                );

        try {
            ClientService clientService = context.getBean(ClientService.class);
            MovieService movieService = context.getBean(MovieService.class);
            RentalService rentalService = context.getBean(RentalService.class);
            UserInterface console = new UserInterface(clientService, movieService, rentalService, executorService);

            console.run();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //MovieControllerClient movieService = new MovieControllerClient();
        //RentalControllerClient rentalService = new RentalControllerClient();


    }
}
