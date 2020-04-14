package Controller;
import Entities.Client;
import Entities.Validators.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ClientControllerClient implements ClientService {
    @Autowired
    ClientService clientService;


    @Override
    public void updateClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        clientService.updateClient(client);
    }

    @Override
    public void addClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        clientService.addClient(client);
    }

    @Override
    public Set<Client> getAllClients() throws SQLException, ParserConfigurationException, TransformerException, SAXException, IOException {
        return clientService.getAllClients();
    }

    @Override
    public void deleteClient(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        clientService.deleteClient(id);
    }

    @Override
    public Set<Client> filterClientsId() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
        return clientService.filterClientsId();
    }

    @Override
    public Set<Client> filterClientsName(int length) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException {
       return clientService.filterClientsName(length);
    }

    @Override
    public Optional<Client> getById(Integer clientId) throws SQLException {
        return clientService.getById(clientId);
    }


}
