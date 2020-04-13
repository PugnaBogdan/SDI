package Controller;

import Entities.Client;
import Entities.Validators.ClientValidator;
import Entities.Validators.ValidatorException;
import Repository.Repository;
import Repository.SpringDB.ClientSpringDBRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author Rares.
 */
public class ClientController implements ClientService {
    @Autowired
    private ClientSpringDBRepo repo;

    @Autowired
    private ClientValidator validator;




    public Optional<Client> getById(Integer clientId) throws SQLException {
        return repo.findOne(clientId);
    }

    public Set<Client> getAllClients() throws SQLException {
        Iterable<Client> clients = repo.findAll();
        Set<Client> set = new HashSet<>();

        clients.forEach(set::add);
        return set;
    }

    public void addClient(Client clientToSave) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {

        try
        {
            validator.validate(clientToSave);
            repo.save(clientToSave);
        }
        catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
        {
            throw new ValidatorException(v.getMessage());
        }

    }


    public void updateClient(Client UpdatedClient) {


        try{
            repo.update(UpdatedClient);

        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
    }




    public void deleteClient(int clientToDelete) throws ValidatorException {

        try {
            repo.delete(clientToDelete);
        } catch (ValidatorException v) {
            throw new ValidatorException((v.getMessage()));
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();

        }
    }



    public Set<Client> filterClientsId() throws SQLException {

           Set<Client> all = new HashSet<>();
           try {
               Iterable<Client> clients = repo.findAll();
               clients.forEach(all::add);
           } catch (SQLException e) {
               e.printStackTrace();
           }
           all.removeIf(client->client.getId()%2!=0);
           return all;


    }

    /*
    filters clients that have the name length less than some number
     */

    public Set<Client> filterClientsName(int length) throws SQLException {

            Set<Client> all = new HashSet<>();
            try {
                Iterable<Client> clients = repo.findAll();
                clients.forEach(all::add);
                all.removeIf(client->client.getName().length() < length);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return all;

    }
}
