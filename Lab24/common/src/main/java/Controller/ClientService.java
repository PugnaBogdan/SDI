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
import java.util.concurrent.CompletableFuture;

public interface ClientService {

    CompletableFuture<Void> addClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Set<Client>> getAllClients() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Void> updateClient(Client client) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Void> deleteClient(int id) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Set<Client>> filterClientsId() throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    CompletableFuture<Set<Client>> filterClientsName(int length) throws ValidatorException, IOException, ParserConfigurationException, SAXException, TransformerException, SQLException;
    Optional<Client> getById(Integer clientId) throws SQLException;
}
