package Controller;

        import Entities.Client;
        import Entities.Validators.ClientValidator;
        import Entities.Validators.ValidatorException;
        import Repository.Repository;
        import Repository.ClientFileRepository;
        import org.xml.sax.SAXException;

        import javax.xml.parsers.ParserConfigurationException;
        import javax.xml.transform.TransformerException;
        import java.io.IOException;
        import java.util.Optional;
        import java.util.Set;

/**
 * @author Rares.
 */
public class ClientController {

    private Repository<Integer, Client> repo;
    private ClientValidator validator;

    public ClientController(Repository<Integer,Client> initRepo)
    {
        this.repo=initRepo;
        validator = new ClientValidator();
    }


    public Optional<Client> getById(Integer clientId)
    {
        return repo.findOne(clientId);
    }

    public Set<Client> getAllClients()
    {
        Iterable<Client> clients = repo.findAll();
        return (Set<Client>) clients;
    }

    public void addClient(Client clientToSave) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException {
        try
        {
            validator.validate(clientToSave);

        }
        catch(ValidatorException v)
        {
            throw new ValidatorException(v.getMessage());
        }
        repo.save(clientToSave);
    }

    public void deleteClient(Integer clientToDelete) throws ValidatorException{
        try{
            repo.delete(clientToDelete);
        }
        catch (ValidatorException v){
            throw  new ValidatorException((v.getMessage()));
        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void updateClient(Client UpdatedClient) {
        try{
            repo.update(UpdatedClient);

        } catch (IOException | ParserConfigurationException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public Set<Client> filterOddId()
    {

        Set<Client> all = (Set<Client>) repo.findAll();
        all.removeIf(client->client.getId()%2!=0);

        return all;
    }

    /*
    filters clients that have the name length less than some number
     */

    public Set<Client> filterClientsWithNameLessThan(int length)
    {
        Set<Client> all = (Set<Client>) repo.findAll();
        all.removeIf(client->client.getName().length() < length);

        return all;
    }
}
