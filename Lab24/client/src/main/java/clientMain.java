import Controller.ClientControllerClient;
import Controller.MovieControllerClient;
import Controller.RentalControllerClient;
import Ui.UserInterface;
import org.xml.sax.SAXException;
import tcp.TcpClient;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class clientMain {
    public static void main(String args[]) throws SQLException, TransformerException, ParserConfigurationException, SAXException, IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        TcpClient tcpClient = new TcpClient();

        ClientControllerClient clientService = new ClientControllerClient(executorService, tcpClient);
        MovieControllerClient movieService = new MovieControllerClient(executorService, tcpClient);
        RentalControllerClient rentalService = new RentalControllerClient(executorService,tcpClient);

        UserInterface console = new UserInterface(clientService,movieService,rentalService);

        console.run();
    }
}
