package Controller;

import Entities.Client;
import Entities.Validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface ClientService {

    void updateClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    void addClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    Set<Client> getAllClients() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    void deleteClient(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    Set<Client> filterClientsId() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    Set<Client> filterClientsName(int length) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    Optional<Client> getById(Integer clientId) throws SQLException;
}
