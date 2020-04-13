package ServerController;

import Controller.ClientController;
import Controller.ClientService;
import Entities.Client;
import Entities.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public class ServerControllerClient implements ClientService {
    @Autowired
    private ClientController clientController;


    @Override
    public void updateClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        clientController.updateClient(client);
    }

    @Override
    public void addClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        clientController.addClient(client);
    }

    @Override
    public Set<Client> getAllClients() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return clientController.getAllClients();
    }

    @Override
    public void deleteClient(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        clientController.deleteClient(id);
    }

    @Override
    public Set<Client> filterClientsId() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return clientController.filterClientsId();
    }

    @Override
    public Set<Client> filterClientsName(int length) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
       return clientController.filterClientsName(length);
    }

    @Override
    public Optional<Client> getById(Integer clientId) throws SQLException {
        return clientController.getById(clientId);
    }
}
