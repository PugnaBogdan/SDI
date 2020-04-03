package Controller;

import Entities.Client;
import Entities.Validators.ClientValidator;
import Entities.Validators.ValidatorException;
import Repository.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * @author Rares.
 */
public class ClientController implements ClientService {

    private Repository<Integer, Client> repo;
    private ClientValidator validator;
    private ExecutorService executorService;

    public ClientController(Repository<Integer, Client> initRepo,ExecutorService exeService)
    {
        this.repo=initRepo;
        validator = new ClientValidator();
        this.executorService = exeService;
    }



    public Optional<Client> getById(Integer clientId) throws SQLException {
        return repo.findOne(clientId);
    }

    public CompletableFuture<Set<Client>> getAllClients() throws SQLException {
        Iterable<Client> clients = repo.findAll();
            return CompletableFuture.supplyAsync (()->{
                return (Set<Client>)clients;
            },executorService);
    }

    public CompletableFuture<Void> addClient(Client clientToSave) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException, SQLException {

        return CompletableFuture.supplyAsync(()->{
            try
            {
                validator.validate(clientToSave);
                repo.save(clientToSave);
                return null;
            }
            catch(ValidatorException | NumberFormatException | ParserConfigurationException | TransformerException | SAXException | IOException | SQLException v)
            {
                throw new ValidatorException(v.getMessage());
            }

    },executorService);
    }


    public CompletableFuture<Void> updateClient(Client UpdatedClient) {

        return CompletableFuture.supplyAsync(()->{
        try{
            repo.update(UpdatedClient);

        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
            return null;
        },executorService);
    }




    public CompletableFuture<Void> deleteClient(int clientToDelete) throws ValidatorException{
        return CompletableFuture.supplyAsync(()->{
        try{
            repo.delete(clientToDelete);
        }
        catch (ValidatorException v){
            throw  new ValidatorException((v.getMessage()));
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException | SQLException e) {
            e.printStackTrace();
        }
            return null;
        },executorService);
}



    public CompletableFuture<Set<Client>> filterClientsId() throws SQLException {
       return CompletableFuture.supplyAsync(()->{

           Set<Client> all = null;
           try {
               all = (Set<Client>) repo.findAll();
           } catch (SQLException e) {
               e.printStackTrace();
           }
           all.removeIf(client->client.getId()%2!=0);
                return all;


        });
    }

    /*
    filters clients that have the name length less than some number
     */

    public CompletableFuture<Set<Client>> filterClientsName(int length) throws SQLException {
        return CompletableFuture.supplyAsync(()->{
            Set<Client> all = null;
            try {
                all = (Set<Client>) repo.findAll();
                all.removeIf(client->client.getName().length() < length);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return all;


        });
    }
}
